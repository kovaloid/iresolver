package com.jresolver.editor.repository.saver;

import com.jresolver.editor.bean.RuleCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


@Component
public class RuleCollectionSaver {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleCollectionSaver.class);

  public void save(RuleCollection ruleCollection) {
    File file = ruleCollection.getFile();
    try (Writer writer = new FileWriter(file)) {
      writer.write("package " + ruleCollection.getPackage() + "\n");
      ruleCollection.getImports().forEach(i -> {
        try {
          writer.write("import  " + i + "\n");
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
      ruleCollection.getGlobals().forEach(global -> {
        try {
          writer.write("global  " + global + "\n");
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
      ruleCollection.getRules().forEach(rule -> {
        try {
          writer.write("rule  " + rule.getName() + "\n");
          writer.write("    when\n");
          writer.write("        " + rule.getConditions() + "\n");
          writer.write("    then\n");
          writer.write("        " + rule.getRecommendations() + "\n");
          writer.write("    end\n\n");
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
