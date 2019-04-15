package com.koval.jresolver.processor.rules.results;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;

public class RulesResultTester {

    @Test
    public void testCreation() throws Exception {
        RulesResult results = new RulesResult();
        List<String> list = new ArrayList<>();

        assertEquals(list, results.getAdvices());
    }

    @Test
    public void testPuting() throws Exception {
        RulesResult results = new RulesResult();
        List<String> list = new ArrayList<>();
        String str = "test";

        results.putAdvice(str);
        list.add(str);

        assertEquals(list, results.getAdvices());
    }

    @Test
    public void testClearing() throws Exception {
        RulesResult results = new RulesResult();
        List<String> list = new ArrayList<>();
        String str = "test";

        results.putAdvice(str);
        results.clearAdvices();

        assertEquals(list, results.getAdvices());
    }

    @Test
    public void testToString() throws Exception {
        RulesResult results = new RulesResult();
        String str = "test";
        results.putAdvice(str);

        String test = "RulesResult{advices=[test]}";

        assertEquals(test, results.toString());
    }
}