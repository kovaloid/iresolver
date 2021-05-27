package com.koval.resolver.processor.issues;

import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import com.koval.resolver.common.api.bean.issue.Attachment;
import com.koval.resolver.common.api.bean.issue.Comment;
import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.issue.User;
import com.koval.resolver.common.api.bean.result.AttachmentResult;
import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.bean.result.Pair;
import com.koval.resolver.common.api.component.connector.IssueClient;
import com.koval.resolver.common.api.configuration.Configuration;
import com.koval.resolver.common.api.configuration.bean.ParagraphVectorsConfiguration;
import com.koval.resolver.common.api.configuration.bean.ProcessorsConfiguration;
import com.koval.resolver.common.api.configuration.bean.processors.IssuesProcessorConfiguration;
import com.koval.resolver.common.api.doc2vec.VectorModel;
import com.koval.resolver.common.api.doc2vec.VectorModelSerializer;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IssuesProcessor.class)
public class IssuesProcessorTest {

    private static final List<Issue> issues = IntStream.range(0, 30).mapToObj(i -> {
        Issue issue = new Issue();
        issue.setKey("key-" + i);
        issue.setLabels(Collections.emptyList());
        issue.setAttachments(Collections.emptyList());
        issue.setComments(Collections.emptyList());
        return issue;
    }).collect(Collectors.toList());

    private static final Map<String, Issue> issueByLabel = issues.stream()
        .collect(Collectors.toMap(Issue::getKey, Function.identity()));

    @Test
    public void testEmptyNearestLabels() throws Exception {
        IssueClient issueClientMock = getIssueClientMock();

        vectorModelMock(Collections.emptyList());

        IssuesProcessor issuesProcessor = new IssuesProcessor(issueClientMock, getConfiguration());

        IssueAnalysingResult result = new IssueAnalysingResult();

        Issue issue = new Issue();
        issue.setAttachments(Collections.emptyList());
        issuesProcessor.run(issue, result);

        assertEquals(result.getOriginalIssue(), issue);
        assertTrue(result.getSimilarIssues().isEmpty());
        assertTrue(result.getProbableLabels().isEmpty());
        assertTrue(result.getQualifiedUsers().isEmpty());
        assertTrue(result.getProbableAttachmentTypes().isEmpty());
    }

    @Test
    public void testSimilarIssues() throws Exception {
        List<Issue> similarIssues = new ArrayList<>(issues.subList(0, 10));
        List<String> nearestLabels = similarIssues.stream().map(Issue::getKey)
            .collect(Collectors.toList());

        // Mock similarity with map
        Random r = new Random();
        Map<String, Double> issuesSimilarity = new HashMap<>();
        for (String label : nearestLabels) {
            issuesSimilarity.put(label, r.nextDouble());
        }

        similarIssues.sort(comparingDouble((issue) -> - issuesSimilarity.get(issue.getKey())));

        VectorModel vectorModelMock = vectorModelMock(nearestLabels);
        mockSimilarityToLabelWithMap(vectorModelMock, issuesSimilarity);

        IssueClient issueClientMock = getIssueClientMock();
        IssuesProcessor issuesProcessor = new IssuesProcessor(issueClientMock, getConfiguration());
        IssueAnalysingResult result = new IssueAnalysingResult();

        Issue issue = getSomeIssue();

        issuesProcessor.run(issue, result);

        assertEquals(result.getOriginalIssue(), issue);
        List<Issue> similarIssuesResultSorted = result.getSimilarIssues().stream()
            .sorted(comparingDouble(p -> -p.getMetric()))
            .map(Pair::getEntity)
            .collect(Collectors.toList());

        assertEquals(similarIssuesResultSorted, similarIssues);
    }

    @Test
    public void testProbableLabels() throws Exception {
        List<Issue> similarIssues = new ArrayList<>(issues.subList(0, 3));
        similarIssues.get(0).setLabels(Arrays.asList("label-1", "label-2"));
        similarIssues.get(1).setLabels(Arrays.asList("label-1", "label-2", "label-3"));
        similarIssues.get(2).setLabels(Collections.singletonList("label-1"));

        List<String> nearestLabels = similarIssues.stream().map(Issue::getKey)
            .collect(Collectors.toList());

        vectorModelMock(nearestLabels, 0);

        IssueClient issueClientMock = getIssueClientMock();
        IssuesProcessor issuesProcessor = new IssuesProcessor(issueClientMock, getConfiguration());
        IssueAnalysingResult result = new IssueAnalysingResult();

        Issue issue = getSomeIssue();

        issuesProcessor.run(issue, result);

        List<Pair<String, Integer>> probableLabels = result.getProbableLabels();

        assertEquals(3, probableLabels.size());
        assertTrue(probableLabels.contains(new Pair<>("label-1", 3)));
        assertTrue(probableLabels.contains(new Pair<>("label-2", 2)));
        assertTrue(probableLabels.contains(new Pair<>("label-3", 1)));
    }

    @Test
    public void testQualifiedUsers() throws Exception {
        List<Issue> similarIssues = new ArrayList<>(issues.subList(0, 3));
        User user1 = new User();
        user1.setName("A");
        User user2 = new User();
        user2.setName("B");
        User user3 = new User();
        user3.setName("C");

        Comment comment1 = new Comment();
        comment1.setAuthor(user1);
        Comment comment2 = new Comment();
        comment2.setAuthor(user2);
        Comment comment3 = new Comment();
        comment3.setAuthor(user3);

        similarIssues.get(0).setAssignee(user1);
        similarIssues.get(0).setComments(Collections.singletonList(comment1));

        similarIssues.get(1).setComments(Arrays.asList(comment1, comment2));

        similarIssues.get(2).setAssignee(user3);
        similarIssues.get(2).setComments(Collections.singletonList(comment3));

        List<String> nearestLabels = similarIssues.stream().map(Issue::getKey)
            .collect(Collectors.toList());

        vectorModelMock(nearestLabels, 0);

        IssueClient issueClientMock = getIssueClientMock();
        IssuesProcessor issuesProcessor = new IssuesProcessor(issueClientMock, getConfiguration());
        IssueAnalysingResult result = new IssueAnalysingResult();

        Issue issue = getSomeIssue();

        issuesProcessor.run(issue, result);

        List<Pair<User, Integer>> qualifiedUsers = result.getQualifiedUsers();

        assertEquals(3, qualifiedUsers.size());
        assertEquals(new Pair<>(user1, 3), qualifiedUsers.get(0));
        assertEquals(new Pair<>(user3, 2), qualifiedUsers.get(1));
        assertEquals(new Pair<>(user2, 1), qualifiedUsers.get(2));
    }

    @Test
    public void testProbableAttachmentTypes() throws Exception {
        Attachment csvAttachment = new Attachment();
        csvAttachment.setFileName("some.csv");
        Attachment txtAttachment = new Attachment();
        txtAttachment.setFileName("some.txt");
        Attachment xmlAttachment = new Attachment();
        xmlAttachment.setFileName("some.xml");

        List<Issue> similarIssues = new ArrayList<>(issues.subList(0, 3));
        similarIssues.get(0).setAttachments(Arrays.asList(csvAttachment, txtAttachment));
        similarIssues.get(1).setAttachments(Arrays.asList(csvAttachment, txtAttachment));
        similarIssues.get(2).setAttachments(Arrays.asList(csvAttachment, xmlAttachment));

        List<String> nearestLabels = similarIssues.stream().map(Issue::getKey)
            .collect(Collectors.toList());

        vectorModelMock(nearestLabels, 0);

        IssueClient issueClientMock = getIssueClientMock();
        IssuesProcessor issuesProcessor = new IssuesProcessor(issueClientMock, getConfiguration());
        IssueAnalysingResult result = new IssueAnalysingResult();

        Issue issue = getSomeIssue();
        issue.setAttachments(Arrays.asList(txtAttachment, xmlAttachment));

        issuesProcessor.run(issue, result);

        List<AttachmentResult> probableAttachmentTypes = result.getProbableAttachmentTypes();
        probableAttachmentTypes.sort(comparingInt(AttachmentResult::getRank).reversed());

        assertEquals(3, probableAttachmentTypes.size());

        AttachmentResult attachmentResult1 = probableAttachmentTypes.get(0);
        assertEquals(".csv", attachmentResult1.getExtension());
        assertEquals(3, attachmentResult1.getRank());
        assertEquals("Table", attachmentResult1.getType());
        assertFalse(attachmentResult1.isPresentInCurrentIssue());

        AttachmentResult attachmentResult2 = probableAttachmentTypes.get(1);
        assertEquals(".txt", attachmentResult2.getExtension());
        assertEquals(2, attachmentResult2.getRank());
        assertEquals("Log", attachmentResult2.getType());
        assertTrue(attachmentResult2.isPresentInCurrentIssue());

        AttachmentResult attachmentResult3 = probableAttachmentTypes.get(2);
        assertEquals(".xml", attachmentResult3.getExtension());
        assertEquals(1, attachmentResult3.getRank());
        assertEquals("Configuration", attachmentResult3.getType());
        assertTrue(attachmentResult3.isPresentInCurrentIssue());
    }

    @Test
    public void testSortUsersByRank() throws Exception {
        vectorModelMock();
        IssueClient issueClientMock = getIssueClientMock();
        IssuesProcessor issuesProcessor = new IssuesProcessor(issueClientMock, getConfiguration());

        List<Pair<User, Integer>> usersSorted = new ArrayList<>(100);
        for (int i = 100; i > 0; i--) {
            usersSorted.add(new Pair<>(new User(), i));
        }

        List<Pair<User, Integer>> users = new ArrayList<>(usersSorted);
        Collections.shuffle(users);

        issuesProcessor.sortUsersByRank(users);

        assertEquals(usersSorted, users);
    }

    private Configuration getConfiguration() {
        IssuesProcessorConfiguration issuesProcessorConfiguration = new IssuesProcessorConfiguration();
        issuesProcessorConfiguration.setVectorModelFile("");

        ParagraphVectorsConfiguration paragraphVectorsConfiguration = new ParagraphVectorsConfiguration();
        paragraphVectorsConfiguration.setLanguage("");

        ProcessorsConfiguration processorsConfiguration = new ProcessorsConfiguration();
        processorsConfiguration.setIssues(issuesProcessorConfiguration);

        Configuration configuration = new Configuration();
        configuration.setProcessors(processorsConfiguration);
        configuration.setParagraphVectors(paragraphVectorsConfiguration);

        return configuration;
    }

    private Issue getSomeIssue() {
        Issue issue = new Issue();
        issue.setAttachments(Collections.emptyList());
        issue.setKey("someKey");

        return issue;
    }

    private void mockSimilarityToLabelWithMap(VectorModel vectorModelMock, Map<String, Double> similarities) {
        Mockito.when(vectorModelMock.similarityToLabel(anyString(), anyString()))
            .thenAnswer(invocation -> {
                String label = invocation.getArgument(1);
                return similarities.get(label);
            });
    }

    private IssueClient getIssueClientMock() {
        IssueClient issueClientMock = Mockito.mock(IssueClient.class);
        Mockito.when(issueClientMock.getIssueByKey(anyString())).thenAnswer(invocation -> {
                String key = invocation.getArgument(0);
                return Optional.ofNullable(issueByLabel.get(key)).orElseThrow(() ->
                    new InvalidUseOfMatchersException(String.format("Argument %s does not match", key)
                    ));
            }
        );

        return issueClientMock;
    }

    private VectorModel vectorModelMock() throws Exception {
        VectorModel vectorModelMock = Mockito.mock(VectorModel.class);

        VectorModelSerializer vectorModelSerializerMock = Mockito.mock(VectorModelSerializer.class);
        Mockito.when(vectorModelSerializerMock.deserialize(any(File.class), anyString()))
            .thenReturn(vectorModelMock);
        whenNew(VectorModelSerializer.class).withAnyArguments()
            .thenReturn(vectorModelSerializerMock);

        return vectorModelMock;
    }

    /** vectorModel with getNearestLabels method always returning nearestLabels param */
    private VectorModel vectorModelMock(List<String> nearestLabels) throws Exception {
        VectorModel vectorModelMock = vectorModelMock();
        Mockito.when(vectorModelMock.getNearestLabels(any(), anyInt())).thenReturn(nearestLabels);

        return vectorModelMock;
    }

    /** vectorModel with getNearestLabels method always returning nearestLabels param
     *  and similarityToLabel method returning similarity param */
    private VectorModel vectorModelMock(List<String> nearestLabels, double similarity)
        throws Exception {
        VectorModel vectorModelMock = vectorModelMock(nearestLabels);
        Mockito.when(vectorModelMock.similarityToLabel(anyString(), anyString()))
            .thenReturn(similarity);

        return vectorModelMock;
    }
}
