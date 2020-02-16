package com.koval.resolver.rule.editor.bean;

import java.util.List;


public class Rule {

  private String name;
  private List<String> attributes;
  private List<String> conditions;
  private List<String> recommendations;

  public Rule() {
  }

  public Rule(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<String> attributes) {
    this.attributes = attributes;
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

  @Override
  public String toString() {
    return "Rule{"
      + "name='" + name + '\''
      + ", attributes=" + attributes
      + ", conditions=" + conditions
      + ", recommendations=" + recommendations
      + '}';
  }
}
