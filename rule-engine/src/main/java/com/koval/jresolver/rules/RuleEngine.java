package com.koval.jresolver.rules;

import org.drools.core.event.DebugAgendaEventListener;
import org.drools.core.event.DebugRuleRuntimeEventListener;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.atlassian.jira.rest.client.domain.Issue;


public class RuleEngine {

  private KieContainer kieContainer;

  public RuleEngine() {
    KieServices kieServices = KieServices.Factory.get();
    kieContainer = kieServices.getKieClasspathContainer();
  }

  public RulesResult execute(Issue actualIssue) {
    KieSession kieSession = kieContainer.newKieSession("JiraRules");
    RulesResult results = new RulesResult();
    kieSession.setGlobal("results", results);

    kieSession.addEventListener(new DebugAgendaEventListener());
    kieSession.addEventListener(new DebugRuleRuntimeEventListener());

    kieSession.insert(actualIssue);

   // List<Comment> myList = Lists.newArrayList(actualIssue.getComments().iterator());

    kieSession.fireAllRules();
    kieSession.dispose();
    return results;
  }

}
