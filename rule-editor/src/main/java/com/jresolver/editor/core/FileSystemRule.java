package com.jresolver.editor.core;

import com.jresolver.editor.bean.Rule;

import java.io.File;
import java.util.List;

public class FileSystemRule {

  private File file;
  private String pack;
  private List<String> imports;
  private List<String> globals;
  private List<Rule> rules;

  public FileSystemRule() {
  }

  public FileSystemRule(File file, String pack, List<String> imports, List<String> globals, List<Rule> rules) {
    this.file = file;
    this.pack = pack;
    this.imports = imports;
    this.globals = globals;
    this.rules = rules;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public String getPackage() {
    return pack;
  }

  public void setPackage(String pack) {
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
