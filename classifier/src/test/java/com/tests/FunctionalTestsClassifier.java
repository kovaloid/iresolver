package com.tests;

import java.io.File;

import com.koval.jresolver.connector.JiraConnector;
import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.connector.configuration.JiraProperties;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.rules.Timeout;
import static org.mockito.Mockito.*;

import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import com.koval.jresolver.classifier.core.Classifier;
import com.koval.jresolver.classifier.core.impl.DocClassifier;
import com.koval.jresolver.manager.Manager;

@Category(FunctionalTests.class)
public class FunctionalTestsClassifier extends Assert {
    private Classifier classifier;

    @Rule
    public final Timeout timeout = new Timeout(180000);

    @Before
    public void setUp() throws Exception {
        Manager.setTest("classifier");
        final ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
        classifier = new DocClassifier(classifierProperties);
    }

    @Test
    public void initWithPassword() throws Exception {
        Classifier classifier1 = new DocClassifier(mock(ClassifierProperties.class), "12345");
    }

    @Test
    public void testPrepare() throws Exception {
        File file = new File(Manager.getDataDirectory() + "DataSet.txt");
        if (file.exists()) {
            file.delete();
        }
        classifier.prepare();
        assertTrue(Manager.checkClassifierPrepare());
    }

    @Test
    public void testConfigure() throws Exception {
        File file = new File(Manager.getDataDirectory() + "VectorModel.zip");
        if (file.exists()) {
            file.delete();
        }
        if (!Manager.checkClassifierPrepare()) {
            classifier.prepare();
        }
        classifier.configure();
        assertTrue(Manager.checkClassifierConfigure());
    }

    @Test
    public void testExecute() throws Exception {
        if (!Manager.checkClassifierConfigure()) {
            if (!Manager.checkClassifierPrepare()) {
                classifier.prepare();
            }
            classifier.configure();
        }
        JiraConnector connector = new JiraConnector(new JiraProperties("connector.properties"));
        classifier.execute(connector.getActualIssues().get(0), Manager.getDataDirectory() + "VectorModel.zip");
    }
}
