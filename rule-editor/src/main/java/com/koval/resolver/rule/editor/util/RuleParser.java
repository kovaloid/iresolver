package com.koval.resolver.rule.editor.util;

import java.util.List;
import java.util.stream.Collectors;


public final class RuleParser {

  public static final String PACKAGE = "package";
  public static final String IMPORT = "import";
  public static final String GLOBAL = "global";
  public static final String RULE = "rule";
  public static final String WHEN = "when";
  public static final String THEN = "then";
  public static final String END = "end";

  private RuleParser() {

  }

  public static String getPackage(String line) {
    return getRestStringAfterWord(line, PACKAGE);
  }

  public static String getImport(String line) {
    return getRestStringAfterWord(line, IMPORT);
  }

  public static String getGlobal(String line) {
    return getRestStringAfterWord(line, GLOBAL);
  }

  public static String getRuleName(String line) {
    return getRestStringAfterWord(line, RULE).replace("\"", "");
  }

  private static String getRestStringAfterWord(String line, String word) {
    return line.trim().substring(word.length()).trim();
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
