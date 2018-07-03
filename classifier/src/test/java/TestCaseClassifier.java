import org.junit.*;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

interface FunctionalTests {/*category marker*/}


@RunWith(Categories.class)
@Categories.IncludeCategory(FunctionalTests.class)
@Suite.SuiteClasses({PojoTestsClassifierProperties.class, FunctionalTestsClassifier.class})
public class TestCaseClassifier extends Assert {

}

