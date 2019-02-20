package com.koval.jresolver.rules;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.processor.IssueProcessingResult;
import com.koval.jresolver.processor.Processor;
import com.koval.jresolver.rules.core.RuleEngine;
import com.koval.jresolver.rules.core.impl.DroolsRuleEngine;

public class RuleEngineProcessor implements Processor {

  private RuleEngine ruleEngine;

  public RuleEngineProcessor() throws Exception {
    ruleEngine = new DroolsRuleEngine();
  }

  @Override
  public void run(Issue issue, IssueProcessingResult result) {
    result.setAdvices(ruleEngine.execute(issue).getAdvices());
  }

}
