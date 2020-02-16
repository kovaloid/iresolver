package com.koval.resolver.rule.editor.repository;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.koval.resolver.rule.editor.bean.RuleCollection;
import com.koval.resolver.rule.editor.repository.loader.RuleCollectionLoader;
import com.koval.resolver.rule.editor.repository.saver.RuleCollectionSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


@Repository
public class RuleCollectionRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleCollectionRepository.class);

  @Autowired
  private RuleCollectionLoader ruleCollectionLoader;

  @Autowired
  private RuleCollectionSaver ruleCollectionSaver;

  @Value("${rules.path}")
  private String path;

  private List<RuleCollection> ruleCollections;

  @PostConstruct
  private void init() {
    this.ruleCollections = ruleCollectionLoader.getRuleCollections();
  }

  public void refreshRepository() {
    ruleCollectionLoader.reload();
    ruleCollections = ruleCollectionLoader.getRuleCollections();
  }

  public RuleCollection save(RuleCollection entity) {
    ruleCollectionSaver.save(entity);
    entity.setId(UUID.randomUUID());
    return entity;
  }

  public Iterable<RuleCollection> saveAll(Iterable<RuleCollection> entities) {
    entities.forEach(entity -> ruleCollectionSaver.save(entity));
    return entities;
  }

  public Optional<RuleCollection> findById(UUID id) {
    return ruleCollections.stream().filter(rc -> id.equals(rc.getId())).findAny();
  }

  public boolean existsById(UUID id) {
    return ruleCollections.stream().anyMatch(rc -> id.equals(rc.getId()));
  }

  public Iterable<RuleCollection> findAll() {
    return ruleCollections;
  }

  public Iterable<RuleCollection> findAllById(Iterable<UUID> ids) {
    return ruleCollections.stream().filter(rc -> {
      for (UUID id: ids) {
        if (id.equals(rc.getId())) {
          return true;
        }
      }
      return false;
    }).collect(Collectors.toList());
  }

  public long count() {
    return ruleCollections.size();
  }

  public void deleteById(UUID id) {
    Optional<RuleCollection> ruleCollection = ruleCollections.stream().filter(rc -> id.equals(rc.getId())).findFirst();
    if (ruleCollection.isPresent()) {
      File file = new File(path, ruleCollection.get().getName() + ".drl");
      if (file.delete()) {
        LOGGER.info("Rule collection file was removed: {}", path);
      } else {
        LOGGER.warn("Could not remove rule collection file: {}", path);
      }
    } else {
      LOGGER.warn("There are no rule collection with id: {}", id.toString());
    }
  }

  public void delete(RuleCollection entity) {
    File file = new File(path, entity.getName() + ".drl");
    if (file.delete()) {
      LOGGER.info("Rule collection file was removed: {}", path);
    } else {
      LOGGER.warn("Could not remove rule collection file: {}", path);
    }
  }

  public void deleteAll(Iterable<RuleCollection> entities) {
    entities.forEach(ruleCollection -> {
      File file = new File(path, ruleCollection.getName() + ".drl");
      if (file.delete()) {
        LOGGER.info("Rule collection file was removed: {}", path);
      } else {
        LOGGER.warn("Could not remove rule collection file: {}", path);
      }
    });
  }

  public void deleteAll() {
    deleteAll(ruleCollections);
  }
}
