package com.koval.resolver.reporter.html;
import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.configuration.bean.reporters.HtmlReporterConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HtmlReportGeneratorTester {
    private static HtmlReportGenerator htmlReportGenerator;
    private static final List<String> enabledProcessors = new ArrayList<>();
    private static final String configurationFile = "htmlReportGeneratorTest.txt";

    @Before
    public void SetUp() throws IOException {
        HtmlReporterConfiguration htmlReporterConfiguration = new HtmlReporterConfiguration();
        htmlReporterConfiguration.setOutputFile(configurationFile);
        htmlReportGenerator = new HtmlReportGenerator(htmlReporterConfiguration, enabledProcessors);
    }

    @Test
    public void TestGenerationWithoutResults() throws IOException {
        List<IssueAnalysingResult> emptyResultsList = new ArrayList<>();
        htmlReportGenerator.generate(emptyResultsList);
        CheckThatFileContainTrueResults("");
    }

    @Test
    public void TestGenerationWithWrongFileName() throws IOException {
        TestLogger testLogger = TestLoggerFactory.getTestLogger(HtmlReportGenerator.class);
        List<IssueAnalysingResult> resultList = new ArrayList<>();
        HtmlReporterConfiguration wrongHtmlReporterConfiguration = new HtmlReporterConfiguration();
        wrongHtmlReporterConfiguration.setOutputFile("");
        new HtmlReportGenerator(wrongHtmlReporterConfiguration, enabledProcessors).generate(resultList);
        Assert.assertEquals(1, testLogger.getLoggingEvents().asList().size());
        String testLoggerMessage = testLogger.getLoggingEvents().asList().toString();
        Assert.assertEquals("Could find the file", testLoggerMessage);
    }

    @Test
    public void TestGenerationWithIssues() throws IOException {
        List<IssueAnalysingResult> issueResultsList = new ArrayList<>();
        int resultsCount = 2;
        for(int i = 0; i < resultsCount; i++)
            issueResultsList.add(CreateOriginalIssue(i));
        htmlReportGenerator.generate(issueResultsList);
        StringBuilder expectedContent = new StringBuilder();
        for(int i = 0; i < resultsCount; i++) {
            AddOriginalIssueToExpect(expectedContent, i);
            expectedContent.append("\n\n");
        }
        CheckThatFileContainTrueResults(expectedContent.toString());
    }

    private void CheckThatFileContainTrueResults(String result) throws IOException {
        InputStream inputStream = new FileInputStream(configurationFile);
        StringBuilder fileContent = new StringBuilder();
        String readLine;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((readLine = bufferedReader.readLine()) != null) {
                fileContent.append(readLine).append('\n');
            }
        }
        Assert.assertEquals(fileContent.toString(), result);
    }

    private IssueAnalysingResult CreateOriginalIssue(int id) {
        Issue issue = new Issue();
        issue.setKey("k" + id);
        issue.setSummary("s" + id);
        IssueAnalysingResult issueAnalysingResult = new IssueAnalysingResult();
        issueAnalysingResult.setOriginalIssue(issue);
        return issueAnalysingResult;
    }

    private void AddOriginalIssueToExpect(StringBuilder expectedContent, int i) {
        expectedContent.append("k" + i + " : s" + i);
    }
}
