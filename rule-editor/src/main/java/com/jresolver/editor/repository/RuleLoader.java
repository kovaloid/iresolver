package com.jresolver.editor.repository;

import com.jresolver.editor.bean.Rule;
import com.jresolver.editor.core.FileSystemRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class RuleLoader {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleLoader.class);

  private final String PATH = "src/main/resources/rules";

  public void init() throws IOException {

    File filePath = new File(PATH);
    if (filePath.exists()) {
      List<File> files = Arrays.stream(Objects.requireNonNull(filePath.listFiles((dir1, filename) -> filename.endsWith(".drl")))).collect(Collectors.toList());
      extractFileSystemRuleObjectsFromFiles(files);
    }
    throw new IOException("Could not find the rules folder: " + PATH);
  }

  private List<FileSystemRule> extractFileSystemRuleObjectsFromFiles(List<File> files) {
    LOGGER.info("Retrieving all the rules from the file system");
    List<FileSystemRule> fileSystemRules = new ArrayList<>();
    files.forEach(file -> {
      try {
        FileSystemRule fileSystemRule = extractFileSystemRuleObjectFromFile(file);
        fileSystemRules.add(fileSystemRule);
      } catch (IOException e) {
        LOGGER.error("Could not extract FileSystemObject from the file with name: " + file.getAbsolutePath(), e);
      }
    });
    return fileSystemRules;
  }

  private FileSystemRule extractFileSystemRuleObjectFromFile(File file) throws IOException {
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

    return new FileSystemRule(file, pack, imports, globals, rules);
  }
}
