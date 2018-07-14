package com;

import java.util.List;

public class Rule {
  private String file;
  private String name;
  private List<String> conditions;
  private List<String> recommendations;

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getConditions() {
    return conditions;
  }

  public void setConditions(List<String> conditions) {
    this.conditions = conditions;
  }

  public List<String> getRecommendations() {
    return recommendations;
  }

  public void setRecommendations(List<String> recommendations) {
    this.recommendations = recommendations;
  }
}
