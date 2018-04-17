package com.koval.jresolver.rules;

import com.atlassian.jira.rest.client.domain.Issue;
import java.util.ArrayList;
import java.util.List;

import org.drools.core.event.DebugAgendaEventListener;
import org.drools.core.event.DebugRuleRuntimeEventListener;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.drools.core.marshalling.impl.ProtobufMessages;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;


public class TestMain {
  public static void main(String[] args) {
    KieServices ks = KieServices.Factory.get();

    // From the kie services, a container is created from the classpath
    KieContainer kc = ks.getKieClasspathContainer();

    execute(kc);
  }

/*
  public static void ccccc() {
    ProtobufMessages.KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    for ( int i = 0; i < rules.length; i++ ) {
      String ruleFile = rules[i];
      System.out.println( "Loading file: " + ruleFile );
      kbuilder.add( ResourceFactory.newClassPathResource( ruleFile,
        RuleRunner.class ),
        ResourceType.DRL );
    }
    Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
    kbase.addKnowledgePackages( pkgs );
    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
  }*/



  public static void execute( KieContainer kc ) {
    // From the container, a session is created based on
    // its definition and configuration in the META-INF/kmodule.xml file
    KieSession ksession = kc.newKieSession("HelloWorldKS");

    // Once the session is created, the application can interact with it
    // In this case it is setting a global as defined in the
    // org/drools/examples/helloworld/HelloWorld.drl file

    List<Object> list = new ArrayList<>();
    RulesResult results = new RulesResult();

    ksession.setGlobal("list", list );
    ksession.setGlobal("results", results );

    // The application can also setup listeners
    ksession.addEventListener( new DebugAgendaEventListener() );
    ksession.addEventListener( new DebugRuleRuntimeEventListener() );

    // To setup a file based audit logger, uncomment the next line
    // KieRuntimeLogger logger = ks.getLoggers().newFileLogger( ksession, "./helloworld" );

    // To setup a ThreadedFileLogger, so that the audit view reflects events whilst debugging,
    // uncomment the next line
    // KieRuntimeLogger logger = ks.getLoggers().newThreadedFileLogger( ksession, "./helloworld", 1000 );

    // The application can insert facts into the session
    final Message message = new Message();
    message.setMessage( "Hello World" );
    message.setStatus( Message.HELLO );
    
    final TestIssue testIssue = new TestIssue();
    testIssue.setDescription("33");
    testIssue.setSomething(new ArrayList<>());



    ksession.insert( message );
    ksession.insert( testIssue );

    // and fire the rules
    ksession.fireAllRules();


    System.out.println(list);
    
    System.out.println(results);

    // Remove comment if using logging
    // logger.close();

    // and then dispose the session
    ksession.dispose();
  }

  public static class Message {
    public static final int HELLO   = 0;
    public static final int GOODBYE = 1;

    private String message;

    private int status;

    public Message() {
    }

    public String getMessage() {
      return this.message;
    }

    public void setMessage(final String message) {
      this.message = message;
    }

    public int getStatus() {
      return this.status;
    }

    public void setStatus(final int status) {
      this.status = status;
    }

    public static Message doSomething(Message message) {
      return message;
    }

    public boolean isSomething(String msg, List<Object> list) {
      list.add( this );
      return this.message.equals( msg );
    }
  }


}
