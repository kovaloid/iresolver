package com.koval.jresolver.processor.rules.core.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.processor.rules.core.RuleEngine;


public class DroolsRuleEngine implements RuleEngine {

  private static final Logger LOGGER = LoggerFactory.getLogger(DroolsRuleEngine.class);
  private final KieSession kieSession;

  public DroolsRuleEngine(Resource[] resources) throws IOException {
    final KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    addRulesToKnowledgeBuilder(knowledgeBuilder, resources);
    checkForErrors(knowledgeBuilder);
    kieSession = createSession(knowledgeBuilder);
    LOGGER.info("Kie session was created.");
  }

  public DroolsRuleEngine() throws IOException {
      this(getDefaultResources());
  }

  private void addRulesToKnowledgeBuilder(KnowledgeBuilder knowledgeBuilder, Resource[] resources) throws IOException {
    if (resources.length == 0) {
      throw new RuntimeException("Could not find any *.drl files.");
    }
    List<String> rules = new LinkedList<>();
    for (Resource resource: resources) {
      if (!rules.contains(resource.getFilename())) {
        if (resource instanceof FileSystemResource) {
          String filePath = resource.getFile().getCanonicalPath();
          LOGGER.info("Add file to rule engine builder: {}", filePath);
          knowledgeBuilder.add(ResourceFactory.newFileResource(filePath), ResourceType.DRL);
        } else if (resource instanceof InputStreamResource) {
          LOGGER.info(resource.getDescription());
          knowledgeBuilder.add(ResourceFactory.newInputStreamResource(resource.getInputStream()), ResourceType.DRL);
        }
      }
      rules.add(resource.getFilename());
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

  private static Resource[] getDefaultResources() throws IOException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader().getClass().getClassLoader();
    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
    return resolver.getResources("classpath*:*.drl");
  }

  public void setDebugMode() {
    kieSession.addEventListener(new DebugAgendaEventListener());
    kieSession.addEventListener(new DebugRuleRuntimeEventListener());
  }

  @Override
  public List<String> execute(Issue actualIssue) {
    List<String> results = new ArrayList<>();
    kieSession.setGlobal("results", results);
    // insert facts into the session
    kieSession.insert(actualIssue);
    kieSession.fireAllRules();
    return results;
  }

  @Override
  public void close() {
    kieSession.dispose();
    LOGGER.info("Kie session was disposed.");
  }
}
