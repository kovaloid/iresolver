package com.jresolver.editor.repository.loader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jresolver.editor.bean.Rule;
import com.jresolver.editor.bean.RuleCollection;


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
        ruleCollections = extractFileSystemRuleObjectsFromFiles(fileList);
      }
    } else {
      LOGGER.error("Could not find the rules folder: " + path);
      if (filePath.mkdirs()) {
        LOGGER.info("The rules folder was created: " + path);
      }
    }
  }

  private List<RuleCollection> extractFileSystemRuleObjectsFromFiles(List<File> files) {
    LOGGER.info("Retrieving all the rules from the file system");
    List<RuleCollection> result = new ArrayList<>();
    files.forEach(file -> {
      try {
        RuleCollection ruleCollection = extractFileSystemRuleObjectFromFile(file);
        result.add(ruleCollection);
      } catch (IOException e) {
        LOGGER.error("Could not extract FileSystemObject from the file with name: " + file.getAbsolutePath(), e);
      }
    });
    return result;
  }

  @SuppressWarnings("PMD")
  private RuleCollection extractFileSystemRuleObjectFromFile(File file) throws IOException {
    byte[] bytes = Files.readAllBytes(file.toPath());
    String ruleFileContent = new String(bytes, StandardCharsets.UTF_8);
    String[] tokens = ruleFileContent.split("\\s+");
    Iterator<String> iterator = Arrays.stream(tokens).iterator();
    boolean whenCompleted = false;
    boolean thenCompleted = false;
    String pack = null;
    List<String> imports = new ArrayList<>();
    List<String> globals = new ArrayList<>();
    List<Rule> rules = new ArrayList<>();
    String rule = null;
    List<String> when = new ArrayList<>();
    List<String> then = new ArrayList<>();
    while (iterator.hasNext()) {
      String token = iterator.next();
      switch (token) {
        case "package":
          pack = iterator.next();
          break;
        case "import":
          imports.add(iterator.next());
          break;
        case "global":
          globals.add(iterator.next());
          break;
        case "rule":
          rule = iterator.next();
          break;
        case "when":
          when.add(iterator.next());
          whenCompleted = false;
          break;
        case "then":
          whenCompleted = true;
          then.add(iterator.next());
          thenCompleted = false;
          break;
        case "end":
          thenCompleted = true;
          rules.add(new Rule(rule, when, then));
          break;
        default:
          if (!whenCompleted) {
            when.add(iterator.next());
          } else if (!thenCompleted) {
            then.add(iterator.next());
          }
          break;
      }
    }
    return new RuleCollection(UUID.nameUUIDFromBytes(bytes), file, pack, imports, globals, rules);
  }

  private Iterator<String> getTokenIterator(File file) throws IOException {
    byte[] bytes = Files.readAllBytes(file.toPath());
    String ruleFileContent = new String(bytes, StandardCharsets.UTF_8);
    String[] tokens = ruleFileContent.split("\\s+");
    return Arrays.stream(tokens).iterator();
  }

  private String getStringFromList(List<String> stringList) {
    String result = "";
    return stringList.stream().reduce(result, String::concat);
  }

  public List<RuleCollection> getRuleCollections() {
    return ruleCollections;
  }
}
