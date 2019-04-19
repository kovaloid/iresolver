package com.koval.jresolver.processor.similarity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.bean.issue.Attachment;
import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.issue.User;
import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.jresolver.common.api.bean.result.Pair;
import com.koval.jresolver.common.api.component.connector.IssueClient;
import com.koval.jresolver.common.api.component.processor.IssueProcessor;
import com.koval.jresolver.processor.similarity.configuration.SimilarityProcessorProperties;
import com.koval.jresolver.processor.similarity.core.dataset.TextDataExtractor;
import com.koval.jresolver.processor.similarity.core.model.VectorModel;
import com.koval.jresolver.processor.similarity.core.model.VectorModelSerializer;


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
    Collection<String> similarIssueKeys = vectorModel.getNearestLabels(textDataExtractor.extract(issue), NUMBER_OF_NEAREST_LABELS);
    List<Pair<Issue, Double>> similarIssuesWithSimilarity = new ArrayList<>();
    List<Pair<String, Integer>> probableLabelsWithCounter = new ArrayList<>();
    List<Pair<User, Integer>> qualifiedUsersWithCounter = new ArrayList<>();
    List<Pair<Attachment, Integer>> probableAttachmentsWithCounter = new ArrayList<>();

    LOGGER.info("Nearest issue keys for {}: {}", issue.getKey(), similarIssueKeys);
    similarIssueKeys.forEach((similarIssueKey) -> {
      Issue similarIssue = issueClient.getIssueByKey(similarIssueKey.trim());


      Double similarity = vectorModel.getSimilarity(similarIssueKey.trim(), similarIssue.getKey().trim());
      similarIssuesWithSimilarity.add(new Pair<>(similarIssue, similarity));


      /*similarIssue.getLabels().forEach(newProbableLabel -> {
        probableLabelsWithCounter.forEach(existingProbableLabelWithCounter -> {
          if (existingProbableLabelWithCounter.getEntity().equals(newProbableLabel)) {
            Integer numberOfLabels = existingProbableLabelWithCounter.getMetric();
            existingProbableLabelWithCounter.setMetric(numberOfLabels + 1);
          } else {
            probableLabelsWithCounter.add(new Pair<>(newProbableLabel, 1));
          }
        });
      });*/

      similarIssue.getLabels().forEach(newProbableLabel -> {
        probableLabelsWithCounter.add(new Pair<>(newProbableLabel, 1));
      });


      if (similarIssue.getAssignee() != null) {
        qualifiedUsersWithCounter.add(new Pair<>(similarIssue.getAssignee(), 1));
      }
      if (similarIssue.getReporter() != null) {
        qualifiedUsersWithCounter.add(new Pair<>(similarIssue.getReporter(), 1));
      }
      similarIssue.getComments().forEach((comment) -> {
        // if (comment.getAuthor() != null) {
          /* BasicUser author = comment.getAuthor();
          users.add(new User(author.getSelf(), author.getName(), author.getDisplayName(), "",
              new ExpandableProperty<>(0), new HashMap<>(), "")); */
        // }
      });


      similarIssue.getAttachments().forEach(attachment -> {
        probableAttachmentsWithCounter.add(new Pair<>(attachment, 1));
      });
    });
    result.setOriginalIssue(issue);
    result.setSimilarIssues(similarIssuesWithSimilarity);
    result.setProbableAttachments(probableAttachmentsWithCounter);
    result.setQualifiedUsers(qualifiedUsersWithCounter);
    result.setProbableLabels(probableLabelsWithCounter);
  }
}
