package com.koval.jresolver.processor.rules;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.processor.api.Processor;
import com.koval.jresolver.processor.result.IssueProcessingResult;
import com.koval.jresolver.processor.rules.core.RuleEngine;
import com.koval.jresolver.processor.rules.core.impl.DroolsRuleEngine;


public class RuleEngineProcessor implements Processor {

  private final RuleEngine ruleEngine;

  public RuleEngineProcessor() throws Exception {
    ruleEngine = new DroolsRuleEngine();
  }

  @Override
  public void run(Issue issue, IssueProcessingResult result) {
    result.setAdvices(ruleEngine.execute(issue).getAdvices());
  }

}
