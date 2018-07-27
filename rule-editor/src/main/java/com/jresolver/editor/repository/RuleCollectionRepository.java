package com.jresolver.editor.repository;

import com.jresolver.editor.bean.RuleCollection;
import com.jresolver.editor.repository.loader.RuleCollectionLoader;
import com.jresolver.editor.repository.saver.RuleCollectionSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Repository
public class RuleCollectionRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleCollectionRepository.class);

  @Autowired
  private RuleCollectionLoader ruleCollectionLoader;

  @Autowired
  private RuleCollectionSaver ruleCollectionSaver;

  private List<RuleCollection> ruleCollections;

  @PostConstruct
  private void init() {
    this.ruleCollections = ruleCollectionLoader.getRuleCollections();
  }

  public RuleCollection save(RuleCollection entity) {
    ruleCollectionSaver.save(entity);
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
      String path = ruleCollection.get().getFile().getAbsolutePath();
      if (ruleCollection.get().getFile().delete()) {
        LOGGER.info("Rule collection file was removed: {}", path);
      } else {
        LOGGER.warn("Could not remove rule collection file: {}", path);
      }
    } else {
      LOGGER.warn("There are no rule collection with id: {}", id.toString());
    }
  }

  public void delete(RuleCollection entity) {
    String path = entity.getFile().getAbsolutePath();
    if (entity.getFile().delete()) {
      LOGGER.info("Rule collection file was removed: {}", path);
    } else {
      LOGGER.warn("Could not remove rule collection file: {}", path);
    }
  }

  public void deleteAll(Iterable<RuleCollection> entities) {
    entities.forEach(ruleCollection -> {
      String path = ruleCollection.getFile().getAbsolutePath();
      if (ruleCollection.getFile().delete()) {
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
