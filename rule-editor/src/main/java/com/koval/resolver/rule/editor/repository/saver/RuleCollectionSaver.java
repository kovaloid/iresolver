package com.koval.resolver.rule.editor.repository.saver;

import java.io.*;
import java.nio.charset.StandardCharsets;

import com.koval.resolver.rule.editor.bean.RuleCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class RuleCollectionSaver {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleCollectionSaver.class);

  @Value("${rules.path}")
  private String path;

  public void save(RuleCollection ruleCollection) {
    File file = new File(path, ruleCollection.getName() + ".drl");
    try (OutputStream outputStream = new FileOutputStream(file);
         Writer writer = new BufferedWriter(outputStream, StandardCharsets.UTF_8)) {
      writer.write("package " + ruleCollection.getPack() + "\n\n");
      ruleCollection.getImports().forEach(i -> {
        try {
          writer.write("import " + i + "\n");
        } catch (IOException e) {
          LOGGER.error("Could not write 'import' declarations", e);
        }
      });
      writer.write("\n");
      ruleCollection.getGlobals().forEach(global -> {
        try {
          writer.write("global " + global + "\n");
        } catch (IOException e) {
          LOGGER.error("Could not write 'global' declarations", e);
        }
      });
      writer.write("\n");
      ruleCollection.getRules().forEach(rule -> {
        try {
          writer.write("rule \"" + rule.getName() + "\"\n");
          writer.write("  when\n");
          StringBuilder conditions = new StringBuilder();
          rule.getConditions().forEach(condition -> {
            conditions.append(condition);
            conditions.append(", ");
          });
          conditions.delete(conditions.lastIndexOf(", "), conditions.length());
          writer.write("    $issue : JiraIssue( " + conditions.toString() + " )\n");
          writer.write("  then\n");
          rule.getRecommendations().forEach(recommendation -> {
            try {
              writer.write("    results.putAdvice(\"" + recommendation + "\");\n");
            } catch (IOException e) {
              LOGGER.error("Could not write recommendations", e);
            }
          });
          writer.write("end\n\n");
        } catch (IOException e) {
          LOGGER.error("Could not write 'rule' declaration: " + rule.getName(), e);
        }
      });
    } catch (IOException e) {
      LOGGER.error("Could not get file with rule collection: " + file.getAbsolutePath(), e);
    }
  }
}
