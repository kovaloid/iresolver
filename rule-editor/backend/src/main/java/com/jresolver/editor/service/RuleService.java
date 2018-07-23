package com.jresolver.editor.service;

import com.jresolver.editor.bean.DraftRule;
import com.jresolver.editor.bean.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class RuleService {

    private final static Logger LOGGER = LoggerFactory.getLogger(RuleService.class);

    public Rule getRuleById(int ruleId) {
        LOGGER.info("Retrieving the rule by id");
        return new Rule();
    }

    public List<Rule> getAllRules() {
        LOGGER.info("Retrieving all the rules in the system");
        return Arrays.asList(new Rule(), new Rule());
    }

    public Rule updateRuleById(int ruleId, DraftRule payload) {
        LOGGER.info("Updating the rule by its id");
        return new Rule();
    }

    public Rule createRule(DraftRule payload) {
        LOGGER.info("Creating new rule");
        return new Rule();
    }
}
