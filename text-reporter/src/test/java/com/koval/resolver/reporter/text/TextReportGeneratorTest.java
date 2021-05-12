package com.koval.resolver.reporter.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.issue.User;
import com.koval.resolver.common.api.bean.result.AttachmentResult;
import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.bean.result.Pair;
import com.koval.resolver.common.api.configuration.bean.reporters.TextReporterConfiguration;

import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

public class TextReportGeneratorTest {

    private static final String FILE_NAME = "test-report-generator.txt";
    private static final String DASH = " - ";
    private static TextReportGenerator textReportGenerator;
    private static final String KEY_VALUE = "key";

    @Before
    public void setUp() {
        TextReporterConfiguration textReporterConfiguration = new TextReporterConfiguration();
        textReporterConfiguration.setOutputFile(FILE_NAME);
        textReportGenerator = new TextReportGenerator(textReporterConfiguration);
    }

    @Test
    public void testGenerateWithEmptyResults() throws IOException {
        List<IssueAnalysingResult> emptyResults = new ArrayList<>();
        textReportGenerator.generate(emptyResults);
        assertThatFileContainString("");
    }

    @Test
    public void testGenerateWithErrorNameFile() {
        TestLogger logger = TestLoggerFactory.getTestLogger(TextReportGenerator.class);
        TextReporterConfiguration errorConfig = new TextReporterConfiguration();
        errorConfig.setOutputFile("");
        new TextReportGenerator(errorConfig).generate(new ArrayList<>());
        Assert.assertEquals(1, logger.getLoggingEvents().asList().size());
        String loggerMessage = logger.getLoggingEvents().asList().get(0).getMessage();
        Assert.assertEquals("Could not save file: ", loggerMessage);
    }

    @Test
    public void testGenerateWithOnlyOriginalIssue() throws IOException {
        List<IssueAnalysingResult> results = new ArrayList<>();
        int numResults = 5;
        for (int i = 0; i < numResults; i++) {
            results.add(createIssueAnalysingResult(i));
        }
        textReportGenerator.generate(results);
        StringBuilder expectedContent = new StringBuilder();
        for (int i = 0; i < numResults; i++) {
            addToContentOriginalIssuesInformation(expectedContent, i);
            expectedContent.append("\n\n");
        }
        assertThatFileContainString(expectedContent.toString());
    }

    private void addToContentOriginalIssuesInformation(StringBuilder expectedContent, int i) {
        expectedContent.append(KEY_VALUE).append(i)
                .append(" : summary").append(i);
    }

    @Test
    public void testGenerateWithProposals() throws IOException {
        List<IssueAnalysingResult> results = new ArrayList<>();
        int numResults = 5;
        for (int i = 0; i < numResults; i++) {
            results.add(createIssueAnalysingResult(i));
        }
        addProposalsToResultList(results);
        textReportGenerator.generate(results);
        StringBuilder expectedContent = new StringBuilder();
        for (int i = 0; i < numResults; i++) {
            addToContentOriginalIssuesInformation(expectedContent, i);
            addToContentProposalsInformation(expectedContent, i);
            expectedContent.append("\n\n");
        }
        assertThatFileContainString(expectedContent.toString());
    }

    private void addToContentProposalsInformation(StringBuilder expectedContent, int i) {
        expectedContent.append("\nproposals: \nproposals").append(i).append('\n');
    }

    @Test
    public void testGenerateWithSimilarIssues() throws IOException {
        List<IssueAnalysingResult> results = new ArrayList<>();
        int numResults = 5;
        for (int i = 0; i < numResults; i++) {
            IssueAnalysingResult res = createIssueAnalysingResult(i);
            addSimilarToResult(res, i);
            addQualifiedUsersToResult(res, i);
            addLabelsToResult(res, i);
            addAttachmentToResult(res, i);
            results.add(res);
        }
        textReportGenerator.generate(results);
        StringBuilder expectedContent = new StringBuilder();
        for (int i = 0; i < numResults; i++) {
            addToContentOriginalIssuesInformation(expectedContent, i);
            addToContentSimilarIssuesInformation(expectedContent, i);
            addToContentUsersInformation(expectedContent, i);
            addToContentLabelsInformation(expectedContent, i);
            addToContentAttachmentInformation(expectedContent, i);
            expectedContent.append("\n\n");
        }
        assertThatFileContainString(expectedContent.toString());
    }

    private void addToContentAttachmentInformation(StringBuilder expectedContent, int i) {
        expectedContent.append("\nattachments: \nextensions")
                .append(DASH).append(i)
                .append(DASH + "type" + DASH + "false\n");
    }

    private void addToContentLabelsInformation(StringBuilder expectedContent, int i) {
        expectedContent.append("\nlabels: \nlabel").append(DASH).append(i).append('\n');
    }

    private void addToContentUsersInformation(StringBuilder expectedContent, int i) {
        expectedContent.append("\nusers: \nName").append(DASH)
                .append("email@mail.com").append(DASH).append(i).append('\n');
    }

    private void addToContentSimilarIssuesInformation(StringBuilder expectedContent, int i) {
        expectedContent.append("\n\nsimilar issues: \n");
        expectedContent.append(KEY_VALUE).append(DASH).append(i + i * 0.10).append('\n');
    }

    private void addAttachmentToResult(IssueAnalysingResult result, int i) {
        AttachmentResult attachmentResult = new AttachmentResult("extensions", i, "type", false);
        result.setProbableAttachmentTypes(Collections.singletonList(attachmentResult));
    }

    private void addSimilarToResult(IssueAnalysingResult result, int i) {
        Issue issue = new Issue();
        issue.setKey(KEY_VALUE);
        result.setSimilarIssues(Collections.singletonList(new Pair<>(issue, i + i * 0.10)));
    }

    private void addQualifiedUsersToResult(IssueAnalysingResult result, int i) {
        User user = new User();
        user.setDisplayName("Name");
        user.setEmailAddress("email@mail.com");
        result.setQualifiedUsers(Collections.singletonList(new Pair<>(user, i)));
    }

    private void addLabelsToResult(IssueAnalysingResult result, int i) {
        result.setProbableLabels(Collections.singletonList(new Pair<>("label", i)));
    }


    private IssueAnalysingResult createIssueAnalysingResult(int num) {
        Issue issue = new Issue();
        issue.setKey(new StringBuilder().append(KEY_VALUE).append(num).toString());
        issue.setSummary("summary" + num);
        IssueAnalysingResult issueAnalysingResult = new IssueAnalysingResult();
        issueAnalysingResult.setOriginalIssue(issue);
        return issueAnalysingResult;
    }

    private void addProposalsToResultList(List<IssueAnalysingResult> results) {
        for (int i = 0; i < results.size(); i++) {
            results.get(i).setProposals(Collections.singletonList("proposals" + i));
        }
    }

    private void assertThatFileContainString(String string) throws IOException {
        InputStream inputStream = new FileInputStream(FILE_NAME);
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line).append('\n');
            }
        }
        Assert.assertEquals(fileContent.toString(), string);
    }

    @After
    public void  tearDown() {
        new File(FILE_NAME).delete();
    }
}
