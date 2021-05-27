package com.koval.resolver.processor.issues.core;

import static org.junit.Assert.assertTrue;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.component.connector.IssueReceiver;
import com.koval.resolver.common.api.configuration.bean.processors.IssuesProcessorConfiguration;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class IssuesDataSetCreatorTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private final List<Issue> testIssues = IntStream.range(0, 5).mapToObj(i -> {
        Issue issue = new Issue();
        issue.setKey("key " + i);
        issue.setDescription("desc " + i);
        issue.setSummary("summ " + i);

        return issue;
    }).collect(Collectors.toList());

    @Test
    public void testIssuesDataSetCreatorOverwrite() throws IOException {
        File output = folder.newFile("dataset");

        IssuesProcessorConfiguration config = new IssuesProcessorConfiguration();
        config.setOverwriteMode(true);
        config.setDataSetFile(output.getAbsolutePath());

        IssueReceiver receiver = getIssueReceiverMock();

        IssuesDataSetCreator creator = new IssuesDataSetCreator(receiver, config);

        creator.create();

        try (Scanner scanner = new Scanner(output)) {
            String result = scanner.useDelimiter("\\Z").next();

            for (Issue issue : testIssues) {
                assertTrue(result.contains(issue.getKey()));
                assertTrue(result.contains(issue.getDescription()));
                assertTrue(result.contains(issue.getSummary()));
            }
        }
    }

    @Test
    public void testIssuesDataSetCreatorNotOverwrite() throws IOException {
        File output = folder.newFile("dataset");

        String initialData = "aaaaaaaaaaaaa\nbbbbbbbbbbb\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            writer.append(initialData);
        }

        IssuesProcessorConfiguration config = new IssuesProcessorConfiguration();
        config.setOverwriteMode(false);
        config.setDataSetFile(output.getAbsolutePath());

        IssueReceiver receiver = getIssueReceiverMock();

        IssuesDataSetCreator creator = new IssuesDataSetCreator(receiver, config);

        creator.create();

        try (Scanner scanner = new Scanner(output)) {
            String result = scanner.useDelimiter("\\Z").next();

            assertTrue(result.startsWith(initialData));

            for (Issue issue : testIssues) {
                assertTrue(result.contains(issue.getKey()));
                assertTrue(result.contains(issue.getDescription()));
                assertTrue(result.contains(issue.getSummary()));
            }
        }
    }

    private IssueReceiver getIssueReceiverMock() {
        final List<List<Issue>> issues = new ArrayList<>();
        issues.add(testIssues.subList(0, 2));
        issues.add(testIssues.subList(2, 3));
        issues.add(testIssues.subList(3, 5));

        return new IssueReceiver() {
            final Iterator<List<Issue>> iterator = issues.iterator();

            @Override
            public boolean hasNextIssues() {
                return iterator.hasNext();
            }

            @Override
            public List<Issue> getNextIssues() {
                return iterator.next();
            }
        };
    }
}
