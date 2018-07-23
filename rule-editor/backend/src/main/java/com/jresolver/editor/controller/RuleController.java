package com.jresolver.editor.controller;

import com.jresolver.editor.bean.DraftRule;
import com.jresolver.editor.bean.Rule;
import com.jresolver.editor.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @RequestMapping(value = "/rest/rules/{ruleId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Rule> getRule(@PathVariable("ruleId") int ruleId, HttpServletRequest request) {
        Rule rule = ruleService.getRuleById(ruleId);
        return new ResponseEntity<>(rule, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/rules", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Rule>> getRules(HttpServletRequest request) {
        List<Rule> rules = ruleService.getAllRules();
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
}
