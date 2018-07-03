import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

@Category(FunctionalTests.class)
public class PojoTestsClassifierProperties extends Assert {
    private ClassifierProperties classifierProperties = null;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUP() throws Exception {
        classifierProperties = new ClassifierProperties("classifier.properties");
    }

    @Test
    public void testDefaultInit() throws Exception  {
        if (classifierProperties == null)
            classifierProperties = new ClassifierProperties("classifier.properties");
        assertTrue(classifierProperties.getMinWordFrequency() == 1);
        assertTrue(classifierProperties.getIterations() == 5);
        assertTrue(classifierProperties.getEpochs() == 1);
        assertTrue(classifierProperties.getLayerSize() == 100);
        assertTrue(classifierProperties.getLearningRate() - 0.025 < 0.001);
        assertTrue(classifierProperties.getWindowSize() == 5);
        assertTrue(classifierProperties.getSampling() == 0);
        assertTrue(!classifierProperties.isTrainWordVectors());
    }

    @Test
    public void testSetMinWordFrequency() {
        classifierProperties.setMinWordFrequency(10);
        assertTrue(classifierProperties.getMinWordFrequency() == 10);
    }

    @Test
    public void testSetIterations() {
        classifierProperties.setIterations(10);
        assertTrue(classifierProperties.getIterations() == 10);
    }

    @Test
    public void testSetEpochs() {
        classifierProperties.setEpochs(10);
        assertTrue(classifierProperties.getEpochs() == 10);
    }

    @Test
    public void testSetLayerSize() {
        classifierProperties.setLayerSize(10);
        assertTrue(classifierProperties.getLayerSize() == 10);
    }

    @Test
    public void testSetLearningRate() {
        classifierProperties.setLearningRate(0.03);
        assertTrue(classifierProperties.getLearningRate() - 0.03 < 0.001);
    }

    @Test
    public void testSetWindowSize() {
        classifierProperties.setWindowSize(10);
        assertTrue(classifierProperties.getWindowSize() == 10);
    }

    @Test
    public void testSetWorkDirectory() {
        classifierProperties.setWorkFolder("test/directory");
        assertTrue(classifierProperties.getWorkFolder().compareTo("test/directory") == 0);
    }
    @After
    public void tearDown() throws Exception {

    }
}