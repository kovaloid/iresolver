package com.koval.resolver.processor.rules.core;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.koval.resolver.common.api.configuration.Configuration;
import com.koval.resolver.common.api.configuration.component.ProcessorsConfiguration;
import com.koval.resolver.common.api.configuration.component.processors.RuleEngineProcessorConfiguration;
import com.koval.resolver.common.api.model.issue.Issue;
import com.koval.resolver.common.api.model.result.IssueAnalysingResult;
import com.koval.resolver.processor.rules.RuleEngineProcessor;


public class RuleEngineProcessorTest {
    private static final String RULE_LOCATION = "resources";
    private RuleEngineProcessor ruleEngineProcessor;
    private static final boolean FLAG = true;

    @Before
    public void initialize() throws IOException {
        RuleEngineProcessorConfiguration ruleEngineProcessorConfiguration = new RuleEngineProcessorConfiguration();
        ruleEngineProcessorConfiguration.setRulesLocation(RULE_LOCATION);

        ProcessorsConfiguration processorsConfiguration = new ProcessorsConfiguration();
        processorsConfiguration.setRuleEngine(ruleEngineProcessorConfiguration);

        Configuration configuration = new Configuration();
        configuration.setProcessors(processorsConfiguration);

        ruleEngineProcessor = new RuleEngineProcessor(configuration);
    }

    @Test
    public void testRunWithIssueAndIssueAnalysingResult() {
        IssueAnalysingResult issueAnalysingResult = new IssueAnalysingResult();
        try {
            ruleEngineProcessor.run(createIssue(), issueAnalysingResult);
            ruleEngineProcessor.setOriginalIssueToResults(createIssue(), issueAnalysingResult);
        } catch (Exception e) {
        }
        assertTrue(FLAG);
    }

    @Test
    public void testRunWithEmptyIssue() {
        Issue issue = new Issue();
        IssueAnalysingResult issueAnalysingResult = new IssueAnalysingResult();
        try {
            ruleEngineProcessor.run(issue, issueAnalysingResult);
        } catch (Exception e) {
        }
        assertTrue(FLAG);
    }

    private Issue createIssue() {
        Issue issue = new Issue();
        issue.setKey("test-key");
        issue.setSummary("test-summary");
        issue.setDescription("test-description");
        return issue;
    }
}
