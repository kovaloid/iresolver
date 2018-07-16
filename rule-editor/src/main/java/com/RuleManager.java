package com;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public final class RuleManager {

  private RuleManager() {
  }

  public static void saveRule(List<Rule> rules) throws IOException {
    File drl = new File(getRulesDir(), rules.get(0).file);
    try (FileWriter writer = new FileWriter(drl)) {
      writer.write("package com.koval.jresolver.rules\n" +
          "\n" +
          "import com.koval.jresolver.connector.bean.JiraIssue;\n" +
          "import com.koval.jresolver.connector.bean.JiraStatus;\n" +
          "\n" +
          "global com.koval.jresolver.rules.results.RulesResult results" + "\n");
      for (final Rule rule : rules) {
        writer.append("\nrule \"").append(rule.name).append("\"\n\t");
        if (rule.attributes != null && rule.attributes.size() > 0) {
          for (final String att : rule.attributes) {
            writer.append(att).append("\n\t");
          }
        }
        writer.append("when\n");
        for (final String condition : rule.conditions) {
          writer.append("\t\t").append(condition).append('\n');
        }
        writer.append("\tthen\n");
        for (final String recommendation : rule.recommendations) {
          writer.append("\t\t").append(recommendation).append('\n');
        }
        writer.append("end\n");
      }
    }
  }

  public static List<Rule> getRules() {
    List<Rule> rules = new LinkedList<>();

    for (final File file : finder()) {
      try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line = reader.readLine();
        while (line != null) {
          if ((line.length() > 4) && line.substring(0, 4).equals("rule")) {
            Rule rule = new Rule();
            rule.file = file.getName();
            rule.name = line.substring(line.indexOf('"') + 1, line.length() - 1);

            line = reader.readLine();
            List<String> attributes = new LinkedList<>();
            while (!line.equals("\twhen")) {
              attributes.add(line.substring(1));
              line = reader.readLine();
            }
            rule.attributes = attributes;

            line = reader.readLine();
            List<String> conditions = new LinkedList<>();
            while (!line.equals("\tthen")) {
              conditions.add(line.substring(2));
              line = reader.readLine();
            }
            rule.conditions = conditions;

            line = reader.readLine();
            List<String> recommendations = new LinkedList<>();
            while (!line.equals("end")) {
              recommendations.add(line.substring(2));
              line = reader.readLine();
            }
            rule.recommendations = recommendations;
            rules.add(rule);
          }
          line = reader.readLine();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return rules;
  }

  private static File[] finder() {
    return getRulesDir().listFiles((dir1, filename) -> filename.endsWith(".drl"));
  }

  private static File getRulesDir() {
    return new File("rule-engine\\src\\main\\resources");
  }
}

class Rule {
  String file;
  String name;
  List<String> attributes;
  List<String> conditions;
  List<String> recommendations;
}
