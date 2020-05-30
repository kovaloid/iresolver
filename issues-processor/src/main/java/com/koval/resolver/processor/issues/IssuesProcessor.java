package com.koval.resolver.processor.issues;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.issue.User;
import com.koval.resolver.common.api.bean.result.AttachmentResult;
import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.bean.result.Pair;
import com.koval.resolver.common.api.component.connector.IssueClient;
import com.koval.resolver.common.api.component.processor.IssueProcessor;
import com.koval.resolver.common.api.configuration.Configuration;
import com.koval.resolver.common.api.doc2vec.TextDataExtractor;
import com.koval.resolver.common.api.doc2vec.VectorModel;
import com.koval.resolver.common.api.doc2vec.VectorModelSerializer;
import com.koval.resolver.common.api.util.AttachmentTypeUtil;

public class IssuesProcessor implements IssueProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(IssuesProcessor.class);
  private static final int NUMBER_OF_NEAREST_LABELS = 10;

  private final TextDataExtractor textDataExtractor = new TextDataExtractor();
  private final IssueClient issueClient;
  private final VectorModel vectorModel;

  public IssuesProcessor(final IssueClient issueClient, final Configuration properties) throws IOException {
    this.issueClient = issueClient;
    final VectorModelSerializer vectorModelSerializer = new VectorModelSerializer();
    final File vectorModelFile = new File(properties.getProcessors().getIssues().getVectorModelFile());
    this.vectorModel = vectorModelSerializer.deserialize(vectorModelFile,
                                                         properties.getParagraphVectors().getLanguage());
  }

  @Override
  public void run(final Issue issue, final IssueAnalysingResult result) {
    setOriginalIssueToResults(issue, result);
    final Collection<String> similarIssueKeys = vectorModel.getNearestLabels(textDataExtractor.extract(issue),
                                                                       NUMBER_OF_NEAREST_LABELS);
    final List<Pair<Issue, Double>> similarIssuesWithSimilarity = new ArrayList<>();
    final Map<String, Integer> probableLabelsMap = new HashMap<>();
    final Map<User, Integer> qualifiedUsersMap = new HashMap<>();
    final Map<String, Integer> probableAttachmentExtensionsMap = new HashMap<>();

    LOGGER.info("Nearest issue keys for {}: {}", issue.getKey(), similarIssueKeys);
    similarIssueKeys.forEach((similarIssueKey) -> {
      final Issue similarIssue = issueClient.getIssueByKey(similarIssueKey.trim());

      final double similarity = vectorModel.similarityToLabel(textDataExtractor.extract(issue), similarIssueKey);
      similarIssuesWithSimilarity.add(new Pair<>(similarIssue, Math.abs(similarity * 100)));

      similarIssue.getLabels().forEach(label -> addEntityOrUpdateMetric(probableLabelsMap, label));
      if (similarIssue.getAssignee() != null && !similarIssue.getAssignee().getName().equals("<unknown>")) {
        addEntityOrUpdateMetric(qualifiedUsersMap, similarIssue.getAssignee());
      }
      similarIssue.getComments().forEach(comment -> addEntityOrUpdateMetric(qualifiedUsersMap, comment.getAuthor()));
      similarIssue.getAttachments().forEach(attachment ->
                                              addEntityOrUpdateMetric(probableAttachmentExtensionsMap,
                                                                      AttachmentTypeUtil.getExtension(attachment)));
    });
    result.setSimilarIssues(similarIssuesWithSimilarity);
    result.setProbableLabels(convertMapToPairList(probableLabelsMap));
    result.setQualifiedUsers(sortUsersByRank(convertMapToPairList(qualifiedUsersMap)));
    result.setProbableAttachmentTypes(
      getAttachmentMetrics(issue, convertMapToPairList(probableAttachmentExtensionsMap)));
  }

  private <E> void addEntityOrUpdateMetric(final Map<E, Integer> map, final E entity) {
    if (map.containsKey(entity)) {
      final Integer oldMetricNumber = map.get(entity);
      map.replace(entity, oldMetricNumber + 1);
    } else {
      map.put(entity, 1);
    }
  }

  private <E> List<Pair<E, Integer>> convertMapToPairList(final Map<E, Integer> map) {
    final List<Pair<E, Integer>> pairList = new ArrayList<>();
    map.forEach((key, value) -> pairList.add(new Pair<>(key, value)));
    return pairList;
  }

  private List<AttachmentResult> getAttachmentMetrics(
    final Issue issue, final List<Pair<String, Integer>> probableAttachmentExtensions) {
    final List<String> currentIssueAttachmentTypes = AttachmentTypeUtil.getExtensions(issue.getAttachments());
    final List<AttachmentResult> attachmentResults = new ArrayList<>();
    for (final Pair<String, Integer> probableAttachmentExtension : probableAttachmentExtensions) {
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

   public List<Pair<User, Integer>> sortUsersByRank(List<Pair<User, Integer>> listQualifiedUsers) {
        listQualifiedUsers.sort((u1, u2) -> {
            if (u1.getMetric().equals(u2.getMetric())) {
                return 0;
            } else if (u1.getMetric() < u2.getMetric()) {
                return 1;
            } else {
                return -1;
            }
        });
        return listQualifiedUsers;
    }

}
