package com;

import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import com.koval.jresolver.classifier.configuration.ClassifierProperties;


@Category(FunctionalTests.class)
public class PojoTestsClassifierProperties extends Assert {
    private ClassifierProperties classifierProperties;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        classifierProperties = new ClassifierProperties("classifier.properties");
    }

    @Test
    public void testDefaultInit() throws Exception  {
        if (classifierProperties == null) {
            classifierProperties = new ClassifierProperties("classifier.properties");
        }
        assertSame(classifierProperties.getMinWordFrequency(), 1);
        assertSame(classifierProperties.getIterations(), 5);
        assertSame(classifierProperties.getEpochs(), 1);
        assertSame(classifierProperties.getLayerSize(), 100);
        assertTrue(classifierProperties.getLearningRate() - 0.025 < 0.001);
        assertSame(classifierProperties.getWindowSize(), 5);
        assertSame(classifierProperties.getSampling(), 0);
        assertFalse(classifierProperties.isTrainWordVectors());
    }

    @Test
    public void testSetMinWordFrequency() {
        classifierProperties.setMinWordFrequency(10);
        assertSame(classifierProperties.getMinWordFrequency(), 10);
    }

    @Test
    public void testSetIterations() {
        classifierProperties.setIterations(10);
        assertSame(classifierProperties.getIterations(), 10);
    }

    @Test
    public void testSetEpochs() {
        classifierProperties.setEpochs(10);
        assertSame(classifierProperties.getEpochs(), 10);
    }

    @Test
    public void testSetLayerSize() {
        classifierProperties.setLayerSize(10);
        assertSame(classifierProperties.getLayerSize(), 10);
    }

    @Test
    public void testSetLearningRate() {
        classifierProperties.setLearningRate(0.03);
        assertTrue(classifierProperties.getLearningRate() - 0.03 < 0.001);
    }

    @Test
    public void testSetWindowSize() {
        classifierProperties.setWindowSize(10);
        assertSame(classifierProperties.getWindowSize(), 10);
    }

    @Test
    public void testSetWorkDirectory() {
        classifierProperties.setWorkFolder("test/directory");
        assertSame(classifierProperties.getWorkFolder().compareTo("test/directory"), 0);
    }
}