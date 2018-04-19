package com.koval.jresolver.rules;

import org.drools.core.event.DebugAgendaEventListener;
import org.drools.core.event.DebugRuleRuntimeEventListener;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.koval.jresolver.connector.bean.JiraIssue;


public class RuleEngine {

  private static final KieServices KIE_SERVICES = KieServices.Factory.get();
  private final KieContainer kieContainer;

  public RuleEngine() {
    // From the kie services, a container is created from the classpath
    kieContainer = KIE_SERVICES.getKieClasspathContainer();
  }

  public RulesResult execute(JiraIssue actualIssue) {
    // From the container, a session is created based on
    // its definition and configuration in the META-INF/kmodule.xml file
    KieSession kieSession = kieContainer.newKieSession("JiraRules");

    // Once the session is created, the application can interact with it
    // In this case it is setting a global as defined in the
    // org/drools/examples/helloworld/HelloWorld.drl file

    RulesResult results = new RulesResult();
    kieSession.setGlobal("results", results);

    // The application can also setup listeners
    kieSession.addEventListener(new DebugAgendaEventListener());
    kieSession.addEventListener(new DebugRuleRuntimeEventListener());

    // To setup a file based audit logger, uncomment the next line
    // KieRuntimeLogger logger = ks.getLoggers().newFileLogger( ksession, "./helloworld" );

    // To setup a ThreadedFileLogger, so that the audit view reflects events whilst debugging,
    // uncomment the next line
    // KieRuntimeLogger logger = ks.getLoggers().newThreadedFileLogger( ksession, "./helloworld", 1000 );

    // The application can insert facts into the session
    kieSession.insert(actualIssue);

    kieSession.fireAllRules();

    // Remove comment if using logging
    // logger.close();

    // and then dispose the session
    kieSession.dispose();
    return results;
  }

}
