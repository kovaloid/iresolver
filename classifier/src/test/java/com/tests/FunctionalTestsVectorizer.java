package com.tests;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import com.koval.jresolver.classifier.core.Classifier;
import com.koval.jresolver.classifier.core.impl.DocClassifier;
import com.koval.jresolver.classifier.core.impl.DocVectorizer;
import com.koval.jresolver.manager.Manager;

@Category(FunctionalTests.class)
public class FunctionalTestsVectorizer {
    private DocVectorizer vectorizer;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Manager.setTest("classifier");
        vectorizer = new DocVectorizer(new ClassifierProperties("classifier.properties"));
    }

    @Test
    public void testCreateFromFile() throws Exception {
        if (!Manager.checkClassifierPrepare()) {
            Classifier classifier = new DocClassifier(new ClassifierProperties("classifier.properties"));
            classifier.prepare();
        }
        File data = new File(Manager.getDataDirectory() + "DataSet.txt");
        vectorizer.createFromFile(data);
    }

    @Test
    public void testEmptyFilename() throws IOException {
        thrown.expect(IOException.class);
        vectorizer.createFromDataset("");
    }

    @Test
    public void testNullPointer() throws Exception {
        thrown.expect(Exception.class);
        vectorizer.createFromFile(null);
    }
}
