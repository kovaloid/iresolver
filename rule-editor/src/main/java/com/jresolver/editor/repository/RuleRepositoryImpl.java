package com.jresolver.editor.repository;

import com.jresolver.editor.bean.Metadata;
import com.jresolver.editor.bean.Rule;
import com.jresolver.editor.core.FileSystemRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;


public class RuleRepositoryImpl implements RuleRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleRepositoryImpl.class);

  private Map<Integer, Metadata> metadata;
  private List<Rule> rules;
  private Integer maxId;

  @PostConstruct
  private void init() throws IOException {
    RuleLoader ruleLoader = new RuleLoader();
    ruleLoader.readRulesFromDrive();
    this.rules = ruleLoader.getRuleList();
    this.metadata = metadata;
    this.maxId = maxId;
  }



  /*private Rule parseRuleContent(String ruleContent) {
    ruleContent.indexOf("rule")
    return new Rule();
  }*/

  @Override
  public Rule save(Rule entity) {
    return null;
  }

  @Override
  public Iterable<Rule> saveAll(Iterable<Rule> entities) {
    return null;
  }

  @Override
  public Optional<Rule> findById(Integer id) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Integer id) {
    return rules.stream().anyMatch(rule -> rule.getId() == id);
  }

  @Override
  public Iterable<Rule> findAll() {
    return rules;
  }

  @Override
  public Iterable<Rule> findAllById(Iterable<Integer> ids) {
    return null;
  }

  @Override
  public long count() {
    return rules.size();
  }

  @Override
  public void deleteById(Integer id) {

  }

  @Override
  public void delete(Rule entity) {

  }

  @Override
  public void deleteAll(Iterable<Rule> entities) {

  }

  @Override
  public void deleteAll() {

  }
}
