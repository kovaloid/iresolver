package com.koval.jresolver.processor.similarity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.issue.User;
import com.koval.jresolver.common.api.bean.result.AttachmentResult;
import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.jresolver.common.api.bean.result.Pair;
import com.koval.jresolver.common.api.component.connector.IssueClient;
import com.koval.jresolver.common.api.component.processor.IssueProcessor;
import com.koval.jresolver.common.api.doc2vec.TextDataExtractor;
import com.koval.jresolver.common.api.doc2vec.VectorModel;
import com.koval.jresolver.common.api.doc2vec.VectorModelSerializer;
import com.koval.jresolver.common.api.util.AttachmentTypeUtil;
import com.koval.jresolver.processor.similarity.configuration.SimilarityProcessorProperties;


public class SimilarityProcessor implements IssueProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimilarityProcessor.class);
  private static final int NUMBER_OF_NEAREST_LABELS = 10;

  private final TextDataExtractor textDataExtractor = new TextDataExtractor();
  private final IssueClient issueClient;
  private final VectorModel vectorModel;

  public SimilarityProcessor(IssueClient issueClient, SimilarityProcessorProperties properties) throws IOException {
    this.issueClient = issueClient;
    VectorModelSerializer vectorModelSerializer = new VectorModelSerializer(properties);
    File vectorModelFile = new File(properties.getWorkFolder(), properties.getVectorModelFileName());
    this.vectorModel = vectorModelSerializer.deserialize(vectorModelFile);
  }

  @Override
  public void run(Issue issue, IssueAnalysingResult result) {
    setOriginalIssueToResults(issue, result);
    Collection<String> similarIssueKeys = vectorModel.getNearestLabels(textDataExtractor.extract(issue),
        NUMBER_OF_NEAREST_LABELS);
    List<Pair<Issue, Double>> similarIssuesWithSimilarity = new ArrayList<>();
    Map<String, Integer> probableLabelsMap = new HashMap<>();
    Map<User, Integer> qualifiedUsersMap = new HashMap<>();
    Map<String, Integer> probableAttachmentExtensionsMap = new HashMap<>();

    LOGGER.info("Nearest issue keys for {}: {}", issue.getKey(), similarIssueKeys);
    similarIssueKeys.forEach((similarIssueKey) -> {
      Issue similarIssue = issueClient.getIssueByKey(similarIssueKey.trim());

      double similarity = vectorModel.similarityToLabel(textDataExtractor.extract(issue), similarIssueKey);
      similarIssuesWithSimilarity.add(new Pair<>(similarIssue, Math.abs(similarity * 100)));

      similarIssue.getLabels().forEach(label -> addEntityOrUpdateMetric(probableLabelsMap, label));
      if (similarIssue.getAssignee() != null && !similarIssue.getAssignee().getName().equals("<unknown>")) {
        addEntityOrUpdateMetric(qualifiedUsersMap, similarIssue.getAssignee());
      }
      similarIssue.getComments().forEach(comment -> addEntityOrUpdateMetric(qualifiedUsersMap, comment.getAuthor()));
      similarIssue.getAttachments().forEach(attachment ->
          addEntityOrUpdateMetric(probableAttachmentExtensionsMap, AttachmentTypeUtil.getExtension(attachment)));
    });
    result.setSimilarIssues(similarIssuesWithSimilarity);
    result.setProbableLabels(convertMapToPairList(probableLabelsMap));
    result.setQualifiedUsers(convertMapToPairList(qualifiedUsersMap));
    result.setProbableAttachmentTypes(getAttachmentMetrics(issue, convertMapToPairList(probableAttachmentExtensionsMap)));
  }

  private <E> void addEntityOrUpdateMetric(Map<E, Integer> map, E entity) {
    if (map.containsKey(entity)) {
      Integer oldMetricNumber = map.get(entity);
      map.replace(entity, oldMetricNumber + 1);
    } else {
      map.put(entity, 1);
    }
  }

  private <E> List<Pair<E, Integer>> convertMapToPairList(Map<E, Integer> map) {
    List<Pair<E, Integer>> pairList = new ArrayList<>();
    map.forEach((key, value) -> pairList.add(new Pair<>(key, value)));
    return pairList;
  }

  private List<AttachmentResult> getAttachmentMetrics(Issue issue, List<Pair<String, Integer>> probableAttachmentExtensions) {
    List<String> currentIssueAttachmentTypes = AttachmentTypeUtil.getExtensions(issue.getAttachments());
    List<AttachmentResult> attachmentResults = new ArrayList<>();
    for (Pair<String, Integer> probableAttachmentExtension: probableAttachmentExtensions) {
      attachmentResults.add(
          new AttachmentResult(
              probableAttachmentExtension.getEntity(),
              probableAttachmentExtension.getMetric(),
              AttachmentTypeUtil.getType(probableAttachmentExtension.getEntity()),
              currentIssueAttachmentTypes.contains(probableAttachmentExtension.getEntity())
          )
      );
    }
    return attachmentResults;
  }
}
