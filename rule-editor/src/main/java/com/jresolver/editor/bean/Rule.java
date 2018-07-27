package com.jresolver.editor.bean;

import java.util.List;

public class Rule {

    private int id; //TODO: change to UUID or remove due to unnecessary
    private String name;
    private String location; //TODO: remove
    private List<String> conditions;
    private List<String> recommendations;
    private String file; //TODO: remove
    private List<String> attributes;

  public Rule() {

  }

  public Rule(String name) {
    this.name = name;
  }

  public Rule(String name, List<String> attributes, List<String> conditions, List<String> recommendations) {
    this.name = name;
    this.attributes = attributes;
    this.conditions = conditions;
    this.recommendations = recommendations;
  }

  public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    /*@Override
    public boolean equals(Object o) {
        if (o instanceof Rule) {
            if (((Rule) o).getId() == this.getId()) {
                return true;
            }
        }
        return false;
    }*/
}
