package com.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.koval.jresolver.classifier.results.ClassifierResult;

@Category(FunctionalTests.class)
public class FunctionalTestsClassifierResult extends Assert {
    private ClassifierResult classifierResult;

    @Before
    public void setUp() throws Exception {
        classifierResult = new ClassifierResult();
    }

    @Test
    public void testSetLabels() {
        List<String> labels = new ArrayList<>();
        labels.add("test1");
        labels.add("test2");
        labels.add("test3");
        classifierResult.setLabels(labels);
        assertSame(labels, classifierResult.getLabels());
    }

    @Test
    public void testSetIssues() {
        List<String> issues = new ArrayList<>();
        issues.add("test10");
        issues.add("test12");
        issues.add("test11");
        classifierResult.setIssues(issues);
        assertSame(issues, classifierResult.getIssues());
    }

    @Test
    public void testSetUsers() {
        List<String> users = new ArrayList<>();
        users.add("test7");
        users.add("test8");
        users.add("test9");
        classifierResult.setUsers(users);
        assertSame(users, classifierResult.getUsers());
    }

    @Test
    public void testSetAttachments() {
        List<String> att = new ArrayList<>();
        att.add("test4");
        att.add("test5");
        att.add("test6");
        classifierResult.setAttachments(att);
        assertSame(att, classifierResult.getAttachments());
    }
}
