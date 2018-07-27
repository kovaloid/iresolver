package com.jresolver.editor.repository.saver;

import java.io.*;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jresolver.editor.bean.RuleCollection;


@Component
public class RuleCollectionSaver {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleCollectionSaver.class);

  public void save(RuleCollection ruleCollection) {
    File file = ruleCollection.getFile();
    try (OutputStream outputStream = new FileOutputStream(file);
         Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
      writer.write("package " + ruleCollection.getPack() + "\n");
      ruleCollection.getImports().forEach(i -> {
        try {
          writer.write("import  " + i + "\n");
        } catch (IOException e) {
          LOGGER.error("Could not write 'import' declarations", e);
        }
      });
      ruleCollection.getGlobals().forEach(global -> {
        try {
          writer.write("global  " + global + "\n");
        } catch (IOException e) {
          LOGGER.error("Could not write 'global' declarations", e);
        }
      });
      ruleCollection.getRules().forEach(rule -> {
        try {
          writer.write("rule  " + rule.getName() + "\n");
          writer.write("  when\n");
          writer.write("    " + rule.getConditions() + "\n");
          writer.write("  then\n");
          writer.write("    " + rule.getRecommendations() + "\n");
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
