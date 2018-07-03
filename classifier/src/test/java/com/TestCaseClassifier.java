package com;

import org.junit.*;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.IncludeCategory(FunctionalTests.class)
@Suite.SuiteClasses({PojoTestsClassifierProperties.class, FunctionalTestsClassifier.class})
public class TestCaseClassifier extends Assert {

}

