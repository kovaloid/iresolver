package com.koval.resolver.rule.editor.bean;

import java.util.List;
import java.util.UUID;


public class RuleCollection {

  private UUID id;
  private String name;
  private String pack;
  private List<String> imports;
  private List<String> globals;
  private List<Rule> rules;

  public RuleCollection() {
  }

  public RuleCollection(UUID id, String name) {
    this.id = id;
    this.name = name;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPack() {
    return pack;
  }

  public void setPack(String pack) {
    this.pack = pack;
  }

  public List<String> getImports() {
    return imports;
  }

  public void setImports(List<String> imports) {
    this.imports = imports;
  }

  public List<String> getGlobals() {
    return globals;
  }

  public void setGlobals(List<String> globals) {
    this.globals = globals;
  }

  public List<Rule> getRules() {
    return rules;
  }

  public void setRules(List<Rule> rules) {
    this.rules = rules;
  }

  @Override
  public String toString() {
    return "RuleCollection{"
      + "id=" + id
      + ", name='" + name + '\''
      + ", pack='" + pack + '\''
      + ", imports=" + imports
      + ", globals=" + globals
      + ", rules=" + rules
      + '}';
  }
}
