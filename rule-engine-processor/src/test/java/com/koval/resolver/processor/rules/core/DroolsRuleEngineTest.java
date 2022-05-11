package com.koval.resolver.processor.rules.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import com.koval.resolver.common.api.model.issue.Issue;
import com.koval.resolver.processor.rules.core.impl.DroolsRuleEngine;


public class DroolsRuleEngineTest {
    private final InputStream inputStream = new ByteArrayInputStream("test-stream".getBytes(StandardCharsets.UTF_8));
    private final InputStream wrongInputStream = new ByteArrayInputStream("wrong-test-stream".getBytes(StandardCharsets.UTF_8));
    private DroolsRuleEngine droolsRuleEngine;
    private boolean flag = true;

    @Before
    public void initialize() throws IOException {
        Resource[] rulesResources = new Resource[1];
        rulesResources[0] = new InputStreamResource(inputStream);
        droolsRuleEngine = new DroolsRuleEngine(rulesResources.toString());
    }

    @Test
    public void testExecuteWithRules() {
        try {
            droolsRuleEngine.execute(createIssue());
            droolsRuleEngine.close();
        } catch (Exception e) {
        }
    }

    @Test
    public void testExecuteWithoutRules() throws IOException {
        try {
            Resource[] emptyRulesResources = new Resource[0];
            droolsRuleEngine = new DroolsRuleEngine(emptyRulesResources.toString());
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Could not find any *.drl files.")) {
                flag = true;
            } else {
                flag = false;
            }
        }
        assertTrue(flag);
    }

    @Test
    public void testExecuteWithMistakes() {
        try {
            Resource[] rulesResources = new Resource[1];
            rulesResources[0] = new InputStreamResource(wrongInputStream);
            droolsRuleEngine.close();
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Unable to compile *.drl files.")) {
                flag = true;
            } else {
                flag = false;
            }
        }
        assertTrue(flag);
    }

    @Test
    public void testSetDebugMode() {
        try {
            droolsRuleEngine.setDebugMode();
            droolsRuleEngine.close();
        } catch (Exception e) {
            flag = false;
        }
        assertTrue(flag);
    }

    private Issue createIssue() {
        Issue issue = new Issue();
        issue.setKey("test-key");
        issue.setSummary("test-summary");
        issue.setDescription("test-description");
        return issue;
    }
}
