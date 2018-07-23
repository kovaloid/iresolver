package com.koval.jresolver.report;

import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import com.koval.jresolver.classifier.core.Classifier;
import com.koval.jresolver.classifier.core.impl.DocClassifier;
import com.koval.jresolver.classifier.results.ClassifierResult;
import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.report.core.ReportGenerator;
import com.koval.jresolver.report.core.impl.HtmlReportGenerator;
import com.koval.jresolver.report.results.TotalResult;
import com.koval.jresolver.rules.core.impl.DroolsRuleEngine;
import com.koval.jresolver.rules.results.RulesResult;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class TestCase {

    private static ReportGenerator reportGenerator;

    @Before
    public void setUp() throws Exception{
        ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
        Classifier classifier = new DocClassifier(classifierProperties);
        reportGenerator = new HtmlReportGenerator(classifier, new DroolsRuleEngine());
    }

    @Test
    public void configurationTest(){
        boolean flag = false;
        reportGenerator.configure();
        File file = new File("../output");
        if (file.exists()) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void totalResultTest() throws Exception{
        Collection<String> collection = new HashSet<>();
        collection.add("testString");
        ClassifierResult classifierResult = new ClassifierResult();
        classifierResult.setIssues(collection);
        classifierResult.setLabels(collection);
        classifierResult.setUsers(collection);
        classifierResult.setAttachments(collection);


        RulesResult ruleResult = new RulesResult();
        ruleResult.putAdvice("testAdvice");

        JiraIssue testIssue = new JiraIssue();
        testIssue.setDescription("testDesc");

        TotalResult totalResult = new TotalResult(testIssue, classifierResult, ruleResult);

        assertEquals(testIssue, totalResult.getIssue());
        assertTrue(totalResult.getIssues().containsAll(collection));
        assertTrue(totalResult.getAdvices().containsAll(ruleResult.getAdvices()));

        List<TotalResult> results = new ArrayList<>();
        results.add(totalResult);

        Method method = reportGenerator.getClass().getDeclaredMethod("fillTemplate", List.class);
        method.setAccessible(true);
        method.invoke(reportGenerator, results);

        byte[] bytes1 = Files.readAllBytes(Paths.get("../output/index.html"));
        byte[] hash1 = MessageDigest.getInstance("MD5").digest(bytes1);

        byte[] bytes2 = Files.readAllBytes(Paths.get(TestCase.class.getClassLoader().getResource("testIndex.html").toURI()));
        byte[] hash2 = MessageDigest.getInstance("MD5").digest(bytes2);

        assertArrayEquals(hash1, hash2);
    }

}
