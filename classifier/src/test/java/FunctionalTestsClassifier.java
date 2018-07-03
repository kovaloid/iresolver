import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import com.koval.jresolver.classifier.core.Classifier;
import com.koval.jresolver.classifier.core.impl.DocClassifier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(FunctionalTests.class)
public class FunctionalTestsClassifier extends Assert{
    private Classifier classifier = null;

    @Before
    public void setUp() throws Exception {
        final ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
        classifier = new DocClassifier(classifierProperties);
    }

    @Test
    public void testPrepare() throws Exception{
        classifier.prepare();
        assertTrue(DocClassifier.class.getClassLoader().getResource("DataSet.txt") != null);
    }

    @Test
    public void testConfigure() throws Exception {
        classifier.prepare();
        classifier.configure();
        assertTrue(DocClassifier.class.getClassLoader().getResource("VectorModel.zip") != null);
    }
}
