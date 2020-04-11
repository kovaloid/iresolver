package com.koval.resolver.common.api.configuration.bean.processors;

import java.util.List;


public class GranularIssuesProcessorConfiguration {

  private List<String> affectedIssueParts;
  private String summaryDataSetFile;
  private String descriptionDataSetFile;
  private String commentsDataSetFile;
  private String summaryVectorModelFile;
  private String descriptionVectorModelFile;
  private String commentsVectorModelFile;

  public List<String> getAffectedIssueParts() {
    return affectedIssueParts;
  }

  public void setAffectedIssueParts(List<String> affectedIssueParts) {
    this.affectedIssueParts = affectedIssueParts;
  }

  public String getSummaryDataSetFile() {
    return summaryDataSetFile;
  }

  public void setSummaryDataSetFile(String summaryDataSetFile) {
    this.summaryDataSetFile = summaryDataSetFile;
  }

  public String getDescriptionDataSetFile() {
    return descriptionDataSetFile;
  }

  public void setDescriptionDataSetFile(String descriptionDataSetFile) {
    this.descriptionDataSetFile = descriptionDataSetFile;
  }

  public String getCommentsDataSetFile() {
    return commentsDataSetFile;
  }

  public void setCommentsDataSetFile(String commentsDataSetFile) {
    this.commentsDataSetFile = commentsDataSetFile;
  }

  public String getSummaryVectorModelFile() {
    return summaryVectorModelFile;
  }

  public void setSummaryVectorModelFile(String summaryVectorModelFile) {
    this.summaryVectorModelFile = summaryVectorModelFile;
  }

  public String getDescriptionVectorModelFile() {
    return descriptionVectorModelFile;
  }

  public void setDescriptionVectorModelFile(String descriptionVectorModelFile) {
    this.descriptionVectorModelFile = descriptionVectorModelFile;
  }

  public String getCommentsVectorModelFile() {
    return commentsVectorModelFile;
  }

  public void setCommentsVectorModelFile(String commentsVectorModelFile) {
    this.commentsVectorModelFile = commentsVectorModelFile;
  }

  @Override
  public String toString() {
    return "GranularIssuesProcessorConfiguration{"
        + "affectedIssueParts=" + affectedIssueParts
        + ", summaryDataSetFile='" + summaryDataSetFile + '\''
        + ", descriptionDataSetFile='" + descriptionDataSetFile + '\''
        + ", commentsDataSetFile='" + commentsDataSetFile + '\''
        + ", summaryVectorModelFile='" + summaryVectorModelFile + '\''
        + ", descriptionVectorModelFile='" + descriptionVectorModelFile + '\''
        + ", commentsVectorModelFile='" + commentsVectorModelFile + '\''
        + '}';
  }
}
