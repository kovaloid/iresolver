package com.koval.resolver.reporter.html;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.koval.resolver.common.api.configuration.component.reporters.HtmlReporterConfiguration;
import com.koval.resolver.common.api.model.issue.Issue;
import com.koval.resolver.common.api.model.result.IssueAnalysingResult;

import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;


public class HtmlReportGeneratorTest {
    private static HtmlReportGenerator htmlReportGenerator;
    private static String templateFile = "html-report-template.vm";
    private static String outputFile = "test-html-report-file.html";
    private static List<String> enabledProcessors = new ArrayList<>();

    @Before
    public void initialize() throws IOException {
        HtmlReporterConfiguration htmlReporterConfiguration = new HtmlReporterConfiguration();
        htmlReporterConfiguration.setHtmlTemplateFileName(templateFile);
        htmlReporterConfiguration.setOutputFile(outputFile);
        htmlReportGenerator = new HtmlReportGenerator(htmlReporterConfiguration, enabledProcessors);
    }

    @Test
    public void testGenerateWithoutResults() {
        List<IssueAnalysingResult> results = new ArrayList<>();
        htmlReportGenerator.generate(results);
    }

    @Test
    public void testGenerateWithIssues() {
        List<IssueAnalysingResult> results = new ArrayList<>();
        int resultsCount = 4;
        for (int i = 0; i < resultsCount; i++) {
            results.add(createIssue(i));
        }
        htmlReportGenerator.generate(results);
    }

    private IssueAnalysingResult createIssue(int id) {
        Issue issue = new Issue();
        issue.setKey("k" + id);
        issue.setSummary("s" + id);
        IssueAnalysingResult issueAnalysingResult = new IssueAnalysingResult();
        issueAnalysingResult.setOriginalIssue(issue);
        return issueAnalysingResult;
    }

    @Test
    public void testGenerateWithWrongFileNames() throws IOException {
        TestLogger logger = TestLoggerFactory.getTestLogger(HtmlReportGenerator.class);
        HtmlReporterConfiguration wrongConfig = new HtmlReporterConfiguration();
        wrongConfig.setHtmlTemplateFileName(templateFile);
        wrongConfig.setOutputFile("");
        new HtmlReportGenerator(wrongConfig, enabledProcessors).generate(new ArrayList<>());
        Assert.assertEquals(3, logger.getLoggingEvents().asList().size());
        Assert.assertEquals("Could not generate report", logger.getLoggingEvents().asList().get(2).getMessage());
    }

    @Test
    public void testOpeningReport() throws IOException {
        List<IssueAnalysingResult> results = new ArrayList<>();
        HtmlReporterConfiguration htmlReporterConfiguration = new HtmlReporterConfiguration();
        htmlReporterConfiguration.setOpenBrowser(true);
        htmlReporterConfiguration.setHtmlTemplateFileName(templateFile);
        htmlReporterConfiguration.setOutputFile(outputFile);
        new HtmlReportGenerator(htmlReporterConfiguration, enabledProcessors).generate(results);
    }
}
