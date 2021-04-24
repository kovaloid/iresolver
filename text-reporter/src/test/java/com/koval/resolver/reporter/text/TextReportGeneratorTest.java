package com.koval.resolver.reporter.text;


import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.issue.User;
import com.koval.resolver.common.api.bean.result.AttachmentResult;
import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.bean.result.Pair;
import com.koval.resolver.common.api.configuration.bean.reporters.TextReporterConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextReportGeneratorTest {

    private static final String FILE_NAME = "test-report-generator.txt";
    private static final String DASH = " - ";
    private static TextReportGenerator textReportGenerator;

    @BeforeAll
    public static void setUp() {
        TextReporterConfiguration textReporterConfiguration = new TextReporterConfiguration();
        textReporterConfiguration.setOutputFile(FILE_NAME);
        textReportGenerator = new TextReportGenerator(textReporterConfiguration);
    }


    @AfterEach
    public void tearDown() {
        new File(FILE_NAME).delete();
    }

    @Test
    public void testGenerateWithEmptyResults() throws IOException {
        List<IssueAnalysingResult> emptyResults = new ArrayList<>();
        textReportGenerator.generate(emptyResults);
        Assertions.assertTrue(isFileContainString(""));
    }

    @Test
    public void testGenerateWithErrorNameFile() {
        TestLogger logger = TestLoggerFactory.getTestLogger(TextReportGenerator.class);
        TextReporterConfiguration errorConfig = new TextReporterConfiguration();
        errorConfig.setOutputFile("");
        new TextReportGenerator(errorConfig).generate(new ArrayList<>());
        Assertions.assertEquals(1, logger.getLoggingEvents().asList().size());
        String loggerMessage = logger.getLoggingEvents().asList().get(0).getMessage();
        Assertions.assertEquals("Could not save file: ", loggerMessage);
        System.out.println();
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
        }
        Assertions.assertTrue(isFileContainString(expectedContent.toString()));
    }

    private void addToContentOriginalIssuesInformation(StringBuilder expectedContent, int i) {
        expectedContent.append("key").append(i)
                .append(" : ").append("summary").append(i)
                .append("\n\n");
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
        Assertions.assertTrue(isFileContainString(expectedContent.toString()));
    }

    private void addToContentProposalsInformation(StringBuilder expectedContent, int i) {
        expectedContent.append("\nproposals: \n");
        expectedContent.append("proposals").append(i).append('\n');
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
        Assertions.assertTrue(isFileContainString(expectedContent.toString()));
    }

    private void addToContentAttachmentInformation(StringBuilder expectedContent, int i) {
        expectedContent.append("\nattachments: \n");
        expectedContent.append("extensions").append(DASH).append(i)
                .append(DASH).append("type")
                .append(DASH).append(false)
                .append('\n');
    }

    private void addToContentLabelsInformation(StringBuilder expectedContent, int i) {
        expectedContent.append("\nlabels: \n");
        expectedContent.append("label").append(DASH).append(i).append("\n");
    }

    private void addToContentUsersInformation(StringBuilder expectedContent, int i) {
        expectedContent.append("\nusers: \n");
        expectedContent.append("Name").append(DASH)
                .append("email@mail.com").append(DASH).append(i).append('\n');
    }

    private void addToContentSimilarIssuesInformation(StringBuilder expectedContent, int i) {
        expectedContent.append("\n\nsimilar issues: \n");
        expectedContent.append("key").append(DASH).append(i + i * 0.10).append('\n');
    }

    private void addAttachmentToResult(IssueAnalysingResult result, int i) {
        AttachmentResult attachmentResult = new AttachmentResult("extensions", i, "type", false);
        result.setProbableAttachmentTypes(Collections.singletonList(attachmentResult));
    }

    private void addSimilarToResult(IssueAnalysingResult result, int i) {
        Issue issue = new Issue();
        issue.setKey("key");
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
        issue.setKey("key" + num);
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

    private boolean isFileContainString(String string) throws IOException {
        InputStream inputStream = new FileInputStream(FILE_NAME);
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        }
        return fileContent.toString().equals(string);
    }
}
