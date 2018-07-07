package com.koval.jresolver.rules.core.impl;

import java.io.File;
import java.util.Collection;

import org.drools.core.event.DebugAgendaEventListener;
import org.drools.core.event.DebugRuleRuntimeEventListener;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.manager.Manager;
import com.koval.jresolver.rules.core.RuleEngine;
import com.koval.jresolver.rules.results.RulesResult;


public class DroolsRuleEngine implements RuleEngine {

  private static final Logger LOGGER = LoggerFactory.getLogger(DroolsRuleEngine.class);
  private final KieSession kieSession;

  public DroolsRuleEngine() {
    final KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    addRulesToKnowledgeBuilder(knowledgeBuilder);
    checkForErrors(knowledgeBuilder);
    kieSession = createSession(knowledgeBuilder);
    LOGGER.info("Kie session was created.");
  }

  private void addRulesToKnowledgeBuilder(KnowledgeBuilder knowledgeBuilder) {
    File dir = new File(Manager.getRulesDirectory());
    String[] rules = dir.list();
    if (rules == null || rules.length == 0) {
      throw new RuntimeException("Could not find any files in /rules.");
    }
    for (String rule: rules) {
      LOGGER.info("Add file to rule engine builder: {}", rule);
      knowledgeBuilder.add(ResourceFactory.newFileResource(Manager.getRulesDirectory() + rule), ResourceType.DRL);
    }
  }

  private void checkForErrors(KnowledgeBuilder knowledgeBuilder) {
    if (knowledgeBuilder.hasErrors()) {
      LOGGER.error(knowledgeBuilder.getErrors().toString());
      throw new RuntimeException("Unable to compile *.drl files.");
    }
  }

  private KieSession createSession(KnowledgeBuilder knowledgeBuilder) {
    final Collection<KiePackage> packages = knowledgeBuilder.getKnowledgePackages();
    final InternalKnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
    knowledgeBase.addPackages(packages);
    return knowledgeBase.newKieSession();
  }

  public void setDebugMode() {
    kieSession.addEventListener(new DebugAgendaEventListener());
    kieSession.addEventListener(new DebugRuleRuntimeEventListener());
  }

  @Override
  public RulesResult execute(JiraIssue actualIssue) {
    RulesResult results = new RulesResult();
    kieSession.setGlobal("results", results);
    // insert facts into the session
    kieSession.insert(actualIssue);
    kieSession.fireAllRules();
    return results;
  }

  @Override
  public void close() throws Exception {
    kieSession.dispose();
    LOGGER.info("Kie session was disposed.");
  }
}
