package com.jresolver.editor.bean;

import java.io.File;
import java.util.List;
import java.util.UUID;


public class RuleCollection {

  private UUID id;
  private File file;
  private String pack;
  private List<String> imports;
  private List<String> globals;
  private List<Rule> rules;

  public RuleCollection() {
  }

  public RuleCollection(UUID id, File file) {
    this.id = id;
    this.file = file;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
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
}
