package com.jresolver.editor.util;

import java.util.List;
import java.util.stream.Collectors;


public final class RuleParser {

  public static final String PACKAGE = "package";
  public static final String IMPORT = "import";
  public static final String GLOBAL = "global";
  public static final String RULE = "rule";
  public static final String WHEN = "when";
  public static final String THEN = "then";

  private RuleParser() {

  }

  public static String getPackage(String line) {
    return line.trim().substring(PACKAGE.length()).trim();
  }

  public static String getImport(String line) {
    return line.trim().substring(IMPORT.length()).trim();
  }

  public static String getGlobal(String line) {
    return line.trim().substring(GLOBAL.length()).trim();
  }

  public static String getRuleName(String line) {
    return line.trim().substring(RULE.length()).trim().replace("\"", "");
  }

  public static List<String> getWhen(List<String> lines) {
    return lines.stream()
      .filter(line -> !line.equals(WHEN))
      .map(line -> line.substring(line.indexOf("(") + 1, line.lastIndexOf(")")).trim())
      .collect(Collectors.toList());
  }

  public static List<String> getThen(List<String> lines) {
    return lines.stream()
      .filter(line -> !line.equals(THEN))
      .map(line -> line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")).trim())
      .collect(Collectors.toList());
  }
}
