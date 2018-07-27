package com.jresolver.editor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jresolver.editor.bean.RuleCollection;
import com.jresolver.editor.repository.RuleCollectionRepository;


@Service
public class RuleCollectionService {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleCollectionService.class);

  @Autowired
  private RuleCollectionRepository ruleCollectionRepository;

  public RuleCollection getById(UUID ruleCollectionId) {
    Optional<RuleCollection> optional = ruleCollectionRepository.findById(ruleCollectionId);
    if (optional.isPresent()) {
      LOGGER.debug("Rule collection with id {} was found", ruleCollectionId.toString());
      return ruleCollectionRepository.findById(ruleCollectionId).get();
    }
    LOGGER.debug("Could not find any rule collection with id {}", ruleCollectionId.toString());
    return new RuleCollection();
  }

  public List<RuleCollection> getAll() {
    List<RuleCollection> target = new ArrayList<>();
    ruleCollectionRepository.findAll().forEach(target::add);
    return target;
  }

  public RuleCollection updateById(UUID ruleCollectionId, RuleCollection payload) {
    payload.setId(ruleCollectionId);
    ruleCollectionRepository.save(payload);
    return payload;
  }

  public RuleCollection create(RuleCollection payload) {
    ruleCollectionRepository.save(payload);
    return payload;
  }

  public RuleCollection deleteById(UUID ruleCollectionId) {
    RuleCollection ruleCollection = ruleCollectionRepository.findById(ruleCollectionId).orElse(null);
    ruleCollectionRepository.deleteById(ruleCollectionId);
    return ruleCollection;
  }
}
