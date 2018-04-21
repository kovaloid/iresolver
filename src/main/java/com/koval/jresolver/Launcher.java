package com.koval.jresolver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import com.koval.jresolver.classifier.core.Classifier;
import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import com.koval.jresolver.classifier.core.impl.DocClassifier;
import com.koval.jresolver.report.core.impl.HtmlReportGenerator;
import com.koval.jresolver.report.core.ReportGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;


public class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  public static void main(String[] args) throws Exception {


    //File f = new File("qwerty");
    //f.mkdirs();

    //System.exit(0);

    //Enumeration<URL> e = Launcher.class.getClassLoader().getResources("placeholder");
    //while (e.hasMoreElements()) {
    //  System.out.println(e.nextElement());
    //}





    //System.out.println(checkDroolsFileExists());
    //System.out.println(checkVectorModelFileExists());

    //System.exit(0);


    if (args.length == 0) {
      //System.out.println("No arguments. Please use 'configure' or 'run'");
      System.out.println("Use default argument 'run'");
      run();
    } else if (args.length == 1) {
      switch (args[0]) {
        case "prepare":
          prepare();
          break;
        case "configure":
          configure();
          break;
        case "run":
          run();
          break;
        default:
          System.out.println("Wrong arguments. Please use 'configure' or 'run'");
          break;
      }
    } else {
      System.out.println("Too much arguments. Please use 'configure' or 'run'");
    }
  }

  private static void prepare() throws URISyntaxException, IOException {
    LOGGER.info("Preparation...");
    ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
    Classifier classifier = new DocClassifier(classifierProperties);
    classifier.prepare();
  }

  private static void configure() throws URISyntaxException, IOException {
    LOGGER.info("Configuration...");
    ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
    Classifier classifier = new DocClassifier(classifierProperties);
    classifier.configure();
  }

  private static void run() throws Exception {
    System.out.println("Generation...");
    //ReportGenerator reportGenerator = new HtmlReportGenerator(classifier, ruleEngine);
    //reportGenerator.generate();
  }


  private static boolean checkVectorModelFileExists() {
    URL vectorModelResource = Launcher.class.getClassLoader().getResource("VectorModel.zip");
    return vectorModelResource != null;
  }

  private static boolean checkDroolsFileExists() throws IOException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader().getClass().getClassLoader();
    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
    Resource[] resources = resolver.getResources("classpath*:*.drl");
    return resources.length > 0;
  }
}
