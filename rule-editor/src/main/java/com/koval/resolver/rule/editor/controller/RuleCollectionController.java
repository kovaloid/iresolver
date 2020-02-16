package com.koval.resolver.rule.editor.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.koval.resolver.rule.editor.bean.RuleCollection;
import com.koval.resolver.rule.editor.service.RuleCollectionService;


@RestController
public class RuleCollectionController {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleCollectionController.class);

  @Autowired
  private RuleCollectionService ruleCollectionService;

  @RequestMapping(value = "/rest/rule-collections/{ruleCollectionId}", method = RequestMethod.GET,
    produces = "application/json")
  public ResponseEntity<RuleCollection> getRuleCollection(@PathVariable("ruleCollectionId") UUID ruleCollectionId) {
    RuleCollection retrievedRuleCollection = ruleCollectionService.getById(ruleCollectionId);
    LOGGER.info("The rule collection with id {} was retrieved", ruleCollectionId.toString());
    return new ResponseEntity<>(retrievedRuleCollection, HttpStatus.OK);
  }

  @RequestMapping(value = "/rest/rule-collections", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<List<RuleCollection>> getRuleCollections() {
    List<RuleCollection> retrievedRuleCollections = ruleCollectionService.getAll();
    LOGGER.info("All the rule collections were retrieved");
    return new ResponseEntity<>(retrievedRuleCollections, HttpStatus.OK);
  }

  @RequestMapping(value = "/rest/rule-collections/{ruleCollectionId}", method = RequestMethod.PUT,
    consumes = "application/json", produces = "application/json")
  public ResponseEntity<RuleCollection> updateRuleCollection(@PathVariable("ruleCollectionId") UUID ruleCollectionId,
                                                             @RequestBody RuleCollection payload) {
    RuleCollection updatedRuleCollection = ruleCollectionService.updateById(ruleCollectionId, payload);
    LOGGER.info("The rule collection with id {} was updated", ruleCollectionId.toString());
    return new ResponseEntity<>(updatedRuleCollection, HttpStatus.OK);
  }

  @RequestMapping(value = "/rest/rule-collections", method = RequestMethod.POST, consumes = "application/json",
    produces = "application/json")
  public ResponseEntity<RuleCollection> createRuleCollection(@RequestBody RuleCollection payload) {
    RuleCollection createdRuleCollection = ruleCollectionService.create(payload);
    LOGGER.info("The rule collection with id {} was created", createdRuleCollection.toString());
    return new ResponseEntity<>(createdRuleCollection, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/rest/rule-collections/{ruleCollectionId}", method = RequestMethod.DELETE,
    consumes = "application/json", produces = "application/json")
  public ResponseEntity<RuleCollection> deleteRuleCollection(@PathVariable("ruleCollectionId") UUID ruleCollectionId) {
    RuleCollection deletedRuleCollection = ruleCollectionService.deleteById(ruleCollectionId);
    LOGGER.info("The rule collection with id {} was deleted", ruleCollectionId.toString());
    return new ResponseEntity<>(deletedRuleCollection, HttpStatus.OK);
  }
}
