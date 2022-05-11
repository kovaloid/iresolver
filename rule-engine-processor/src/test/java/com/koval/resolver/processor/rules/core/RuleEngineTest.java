package com.koval.resolver.processor.rules.core;

import org.junit.Test;

import com.koval.resolver.common.api.model.issue.Issue;


public class RuleEngineTest {
    private RuleEngine ruleEngine;

    @Test
    public void testExecute() {
        Issue issue = new Issue();
        issue.setKey("K" + 1);
        issue.setSummary("S" + 1);
        try {
            ruleEngine.execute(issue);
        } catch (Exception ignored) {

        }
    }
}
