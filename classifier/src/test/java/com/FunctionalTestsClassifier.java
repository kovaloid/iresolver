package com;

import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.rules.Timeout;

import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import com.koval.jresolver.classifier.core.Classifier;
import com.koval.jresolver.classifier.core.impl.DocClassifier;

@Category(FunctionalTests.class)
public class FunctionalTestsClassifier extends Assert {
    private Classifier classifier;

    @Rule
    public final Timeout timeout = new Timeout(180000);

    @Before
    public void setUp() throws Exception {
        final ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
        classifier = new DocClassifier(classifierProperties);
    }

    @Test
    public void testPrepare() throws Exception {
        classifier.prepare();
        assertNotNull(DocClassifier.class.getClassLoader().getResource("DataSet.txt"));
    }

    /*@Test
    public void testConfigure() throws Exception {
        classifier.prepare();
        classifier.configure();
        assertNotNull(DocClassifier.class.getClassLoader().getResource("VectorModel.zip"));
    }*/
}
