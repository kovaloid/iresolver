package com.koval.resolver.rule.editor.repository.loader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.koval.resolver.rule.editor.bean.Rule;
import com.koval.resolver.rule.editor.bean.RuleCollection;
import com.koval.resolver.rule.editor.util.RuleParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class RuleCollectionLoader {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleCollectionLoader.class);

  private List<RuleCollection> ruleCollections;

  @Value("${rules.path}")
  private String path;

  @PostConstruct
  public void reload() {
    File filePath = new File(path);
    if (filePath.exists()) {
      File[] files = filePath.listFiles();
      if (files == null) {
        ruleCollections = new ArrayList<>();
      } else {
        List<File> fileList = Arrays.stream(files).filter(file -> file.getName().endsWith(".drl")).collect(Collectors.toList());
        ruleCollections = extractRuleCollectionObjectsFromFiles(fileList);
      }
    } else {
      LOGGER.error("Could not find the rules folder: " + path);
      if (filePath.mkdirs()) {
        LOGGER.info("The rules folder was created: " + path);
      }
    }
  }

  private List<RuleCollection> extractRuleCollectionObjectsFromFiles(List<File> files) {
    LOGGER.info("Retrieving all the rules from the file system");
    List<RuleCollection> result = new ArrayList<>();
    files.forEach(file -> {
      try {
        RuleCollection ruleCollection = extractRuleCollectionObjectFromFile(file);
        LOGGER.info("Rule collection was extracted: {}", ruleCollection);
        result.add(ruleCollection);
      } catch (IOException e) {
        LOGGER.error("Could not extract FileSystemObject from the file with name: " + file.getAbsolutePath(), e);
      }
    });
    return result;
  }

  private RuleCollection extractRuleCollectionObjectFromFile(File file) throws IOException {
    try (InputStream inputStream = new FileInputStream(file);
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(inputStreamReader)) {
      String line = reader.readLine();

      String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
      RuleCollection ruleCollection = new RuleCollection(getIdForRuleCollection(file), fileName);
      List<String> imports = new ArrayList<>();
      List<String> globals = new ArrayList<>();
      List<Rule> rules = new ArrayList<>();

      while (line != null) {
        if (line.contains(RuleParser.PACKAGE)) {
          ruleCollection.setPack(RuleParser.getPackage(line));
        } else if (line.contains(RuleParser.IMPORT)) {
          imports.add(RuleParser.getImport(line));
        } else if (line.contains(RuleParser.GLOBAL)) {
          globals.add(RuleParser.getGlobal(line));
        } else if (line.contains(RuleParser.RULE)) {
          Rule rule = new Rule(RuleParser.getRuleName(line));
          fillRule(reader, rule);
          rules.add(rule);
        }
        line = reader.readLine();
      }

      ruleCollection.setImports(imports);
      ruleCollection.setGlobals(globals);
      ruleCollection.setRules(rules);
      return ruleCollection;
    }
  }

  private void fillRule(BufferedReader reader, Rule rule) throws IOException {
    String line = reader.readLine();

    List<String> attributes = new ArrayList<>();
    while (line != null && !line.contains(RuleParser.WHEN)) {
      attributes.add(line.trim());
      line = reader.readLine();
    }

    List<String> when = new ArrayList<>();
    while (line != null && !line.contains(RuleParser.THEN)) {
      when.add(line.trim());
      line = reader.readLine();
    }

    List<String> then = new ArrayList<>();
    while (line != null && !line.contains(RuleParser.END)) {
      then.add(line.trim());
      line = reader.readLine();
    }

    rule.setAttributes(attributes);
    rule.setConditions(RuleParser.getWhen(when));
    rule.setRecommendations(RuleParser.getThen(then));
  }

  private UUID getIdForRuleCollection(File file) throws IOException {
    byte[] bytes = Files.readAllBytes(file.toPath());
    return UUID.nameUUIDFromBytes(bytes);
  }

  public List<RuleCollection> getRuleCollections() {
    return ruleCollections;
  }
}
