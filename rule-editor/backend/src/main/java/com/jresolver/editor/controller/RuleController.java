package com.jresolver.editor.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jresolver.editor.bean.DraftRule;
import com.jresolver.editor.bean.Rule;
import com.jresolver.editor.service.RuleService;

@RestController
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @RequestMapping(value = "/rest/rules/{ruleId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Rule> getRule(@PathVariable("ruleId") int ruleId, HttpServletRequest request) {
        Rule rule = ruleService.getRuleById(ruleId);
        return new ResponseEntity<>(rule, HttpStatus.OK);
    }

    //throws IOException? O_o
    @RequestMapping(value = "/rest/rules", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Rule>> getRules(HttpServletRequest request) throws IOException {
        List<Rule> rules = ruleService.getRulesList();
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/rules/{ruleId}", method = RequestMethod.PUT, consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Rule> updateRule(@PathVariable("ruleId") int ruleId, @RequestBody DraftRule payload) {
        Rule updatedRule = ruleService.updateRuleById(ruleId, payload);
        return new ResponseEntity<>(updatedRule, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/rest/rules", method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Rule> createRule(@RequestBody DraftRule payload, HttpServletRequest request) {
        Rule createdRule = ruleService.createRule(payload);
        return new ResponseEntity<>(createdRule, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/rest/rules/new", method = RequestMethod.GET, consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Rule> getNewRule(HttpServletRequest request) {
        return new ResponseEntity<>(ruleService.getNewRule(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/rest/rules/{ruleId}", method = RequestMethod.DELETE, consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Rule> deleteRule(@PathVariable("ruleId") int ruleId, HttpServletRequest request) {
        return new ResponseEntity<>(ruleService.deleteRule(ruleId), HttpStatus.ACCEPTED);
    }

    //TODO: add mapping to saveRulesToDrive()
}
