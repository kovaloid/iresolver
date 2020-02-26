package com.koval.resolver.processor.issues.granular;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.component.connector.IssueClient;
import com.koval.resolver.common.api.component.processor.IssueProcessor;
import com.koval.resolver.common.api.configuration.Configuration;
import com.koval.resolver.common.api.doc2vec.VectorModel;
import com.koval.resolver.common.api.doc2vec.VectorModelSerializer;
import com.koval.resolver.common.api.util.TextUtil;


@SuppressWarnings("PMD")
public class GranularIssuesProcessor implements IssueProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(GranularIssuesProcessor.class);
  private static final int NUMBER_OF_NEAREST_LABELS = 10;

  private final IssueClient issueClient;
  private final VectorModel summaryVectorModel;
  private final VectorModel descriptionVectorModel;
  private final VectorModel commentsVectorModel;

  public GranularIssuesProcessor(IssueClient issueClient, Configuration properties) throws IOException {
    this.issueClient = issueClient;
    VectorModelSerializer vectorModelSerializer = new VectorModelSerializer();
    File summaryVectorModelFile = new File(properties.getProcessors().getGranularIssues().getSummaryVectorModelFile());
    File descriptionVectorModelFile = new File(properties.getProcessors().getGranularIssues().getDescriptionVectorModelFile());
    File commentsVectorModelFile = new File(properties.getProcessors().getGranularIssues().getCommentsVectorModelFile());
    this.summaryVectorModel = vectorModelSerializer.deserialize(summaryVectorModelFile, properties.getParagraphVectors().getLanguage());
    this.descriptionVectorModel = vectorModelSerializer.deserialize(descriptionVectorModelFile, properties.getParagraphVectors().getLanguage());
    this.commentsVectorModel = vectorModelSerializer.deserialize(commentsVectorModelFile, properties.getParagraphVectors().getLanguage());
  }

  @Override
  public void run(Issue issue, IssueAnalysingResult result) {
    setOriginalIssueToResults(issue, result);

    Map<String, Integer> similarKeysWithRank = new HashMap<>();

    if (summaryVectorModel != null) {
      Collection<String> similarIssueKeysBySummary = summaryVectorModel
          .getNearestLabels(TextUtil.simplify(issue.getSummary()), NUMBER_OF_NEAREST_LABELS);

      calculateKeys(similarKeysWithRank, similarIssueKeysBySummary);
    }
    if (descriptionVectorModel != null) {
      Collection<String> similarIssueKeysByDescription = descriptionVectorModel
          .getNearestLabels(TextUtil.simplify(issue.getDescription()), NUMBER_OF_NEAREST_LABELS);

      calculateKeys(similarKeysWithRank, similarIssueKeysByDescription);
    }
    if (commentsVectorModel != null) {
      StringBuilder comments = new StringBuilder();
      issue.getComments().forEach(comment -> comments.append(comment.getBody()));
      Collection<String> similarIssueKeysByComments = commentsVectorModel
          .getNearestLabels(TextUtil.simplify(comments.toString()), NUMBER_OF_NEAREST_LABELS);

      calculateKeys(similarKeysWithRank, similarIssueKeysByComments);
    }

    System.out.println(similarKeysWithRank);

    // TODO: implement
  }

  private void calculateKeys(Map<String, Integer> similarKeysWithRank, Collection<String> similarIssueKeysByPart) {
    if (similarKeysWithRank.isEmpty()) {
      similarIssueKeysByPart.forEach(k -> similarKeysWithRank.put(k, 1));
    } else {
      similarIssueKeysByPart.forEach(k -> similarKeysWithRank.merge(k, 1, Integer::sum));
    }
  }
}
