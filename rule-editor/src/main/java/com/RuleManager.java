package com;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public final class RuleManager {

  private RuleManager() {
  }

  public static void saveRule(Rule rule) {
  }

  public static List<Rule> getRules() {
    List<Rule> rules = new LinkedList<>();

    for (final File file : finder("rule-engine\\src\\main\\resources")) {
      System.out.println(file.getName());
      Rule rule = new Rule();
      rule.setFile(file.getName());
      try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line = reader.readLine();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return rules;
  }

  private static File[] finder(String dirName) {
    File dir = new File(dirName);
    return dir.listFiles((dir1, filename) -> filename.endsWith(".drl"));
  }
}
