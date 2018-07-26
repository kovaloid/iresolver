package com.jresolver.editor.controller;

import com.jresolver.editor.bean.RuleCollection;
import com.jresolver.editor.service.RuleCollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
public class RuleCollectionController {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleCollectionController.class);

  @Autowired
  private RuleCollectionService ruleCollectionService;

  @RequestMapping(value = "/rest/rule-collections/{ruleCollectionId}", method = RequestMethod.GET,
    produces = "application/json")
  public ResponseEntity<RuleCollection> getRuleCollection(@PathVariable("ruleCollectionId") UUID ruleCollectionId) {
    RuleCollection ruleCollection = ruleCollectionService.getById(ruleCollectionId);
    return new ResponseEntity<>(ruleCollection, HttpStatus.OK);
  }

  @RequestMapping(value = "/rest/rule-collections", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<List<RuleCollection>> getRuleCollections() {
    List<RuleCollection> ruleCollections = ruleCollectionService.getAll();
    return new ResponseEntity<>(ruleCollections, HttpStatus.OK);
  }

  @RequestMapping(value = "/rest/rule-collections/{ruleCollectionId}", method = RequestMethod.PUT,
    consumes = "application/json", produces = "application/json")
  public ResponseEntity<RuleCollection> updateRuleCollection(@PathVariable("ruleCollectionId") UUID ruleCollectionId,
                                                             @RequestBody RuleCollection payload) {
    RuleCollection updatedRuleCollection = ruleCollectionService.updateById(ruleCollectionId, payload);
    return new ResponseEntity<>(updatedRuleCollection, HttpStatus.ACCEPTED);
  }

  @RequestMapping(value = "/rest/rule-collections", method = RequestMethod.POST, consumes = "application/json",
    produces = "application/json")
  public ResponseEntity<RuleCollection> createRuleCollection(@RequestBody RuleCollection payload) {
    RuleCollection createdRuleCollection = ruleCollectionService.create(payload);
    return new ResponseEntity<>(createdRuleCollection, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/rest/rule-collections/{ruleCollectionId}", method = RequestMethod.DELETE,
    consumes = "application/json", produces = "application/json")
  public ResponseEntity<RuleCollection> deleteRuleCollection(@PathVariable("ruleCollectionId") UUID ruleCollectionId) {
    RuleCollection deletedRuleCollection = ruleCollectionService.deleteById(ruleCollectionId);
    return new ResponseEntity<>(deletedRuleCollection, HttpStatus.ACCEPTED);
  }
}
