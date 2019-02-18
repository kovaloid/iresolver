package com.koval.jresolver.report;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.apache.uima.pear.util.FileUtil;
import org.junit.Before;
import org.junit.Test;

import com.koval.jresolver.classifier.core.Classifier;
import com.koval.jresolver.classifier.results.ClassifierResult;
import com.koval.jresolver.connector.jira.bean.JiraIssue;
import com.koval.jresolver.report.core.ReportGenerator;
import com.koval.jresolver.report.core.impl.HtmlReportGenerator;
import com.koval.jresolver.report.results.TotalResult;
import com.koval.jresolver.rules.core.impl.DroolsRuleEngine;
import com.koval.jresolver.rules.results.RulesResult;


public class ReportGeneratorTests {

    private ReportGenerator reportGenerator;

    @Before
    public void setUp() {
        reportGenerator = new HtmlReportGenerator(mock(Classifier.class), mock(DroolsRuleEngine.class));
    }

    @Test
    public void configurationTest() throws Exception {
        File file = new File("../output");
        if (file.exists() && !FileUtil.deleteDirectory(file)) {
            return; //file system error, directory founded but not deleted.
        }
        boolean flag = false;
        reportGenerator.configure();
        if (file.exists()) {
            flag = true;
        }
        assertTrue(flag);
        flag = false;
        reportGenerator.configure();
        if (file.exists()) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void totalResultTest() throws Exception {
        Collection<String> collection = new HashSet<>();
        collection.add("test1");
        ClassifierResult classifierResult = new ClassifierResult();
        classifierResult.setIssues(collection);
        classifierResult.setLabels(collection);
        classifierResult.setUsers(collection);
        classifierResult.setAttachments(collection);

        RulesResult ruleResult = new RulesResult();
        ruleResult.putAdvice("test2");

        JiraIssue testIssue = new JiraIssue();
        testIssue.setDescription("test3");

        TotalResult totalResult = new TotalResult(testIssue, classifierResult, ruleResult);

        assertEquals(testIssue, totalResult.getIssue());
        assertTrue(totalResult.getIssues().containsAll(collection));
        assertTrue(totalResult.getAdvices().containsAll(ruleResult.getAdvices()));

        Set<String> set = new HashSet<>();
        set.add("testString");

        totalResult.setAdvices(new HashSet<String>() { { add("testAdvice"); } });
        testIssue.setDescription("testDesc");
        totalResult.setIssue(testIssue);
        totalResult.setIssues(set);
        totalResult.setLabels(set);
        totalResult.setUsers(set);
        totalResult.setAttachments(set);

        List<TotalResult> results = new ArrayList<>();
        results.add(totalResult);

        Method method = reportGenerator.getClass().getDeclaredMethod("fillTemplate", List.class);
        method.setAccessible(true);
        method.invoke(reportGenerator, results);

        byte[] bytes1 = Files.readAllBytes(Paths.get("../output/index.html"));
        byte[] hash = MessageDigest.getInstance("MD5").digest(bytes1);

        byte[] bytes2 = Files.readAllBytes(Paths.get(ReportGeneratorTests.class.getClassLoader().getResource("testIndex.html").toURI()));
        byte[] expected = MessageDigest.getInstance("MD5").digest(bytes2);
//        byte[] expected = new byte[]{22, 15, -31, 18, -70, -14, -107, -15, -27, 48, 110, 77, -121, 5, 60, 115};
        //it works on my pc but does`nt on cloud. Why?

        assertArrayEquals(hash, expected);
    }

}
