package com.koval.jresolver.rules.core;

import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Test;

import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.rules.core.impl.DroolsRuleEngine;

public class RuleEngineTester {

    @Test
    public void testCreating() throws Exception {
        boolean flag = true;
        try {
            DroolsRuleEngine test = new DroolsRuleEngine();
            test.close();
        } catch (Exception e) { //Not IOException, becouse close() throws Exception
            flag = false;
        }

        assertTrue(flag);
    }

    @Test
    public void testExecuting() throws Exception {
        JiraIssue issue = new JiraIssue();
        issue.setKey("test_issue");
        issue.setDescription("test_description");
        issue.setComments(new ArrayList<>());

        boolean flag = true;
        try {
            DroolsRuleEngine test = new DroolsRuleEngine();
            test.execute(issue);
            test.close();
        } catch (Exception e) {
            flag = false;
        }

        assertTrue(flag);
    }

    @Test
    public void testSettingDebugMode() throws Exception {
        boolean flag = true;
        try {
            DroolsRuleEngine test = new DroolsRuleEngine();
            test.setDebugMode();
            test.close();
        } catch (Exception e) {
            flag = false;
        }

        assertTrue(flag);
    }
}