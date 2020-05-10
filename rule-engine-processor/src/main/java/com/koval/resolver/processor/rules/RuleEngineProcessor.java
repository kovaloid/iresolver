package com.koval.resolver.processor.rules;

import java.io.IOException;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.component.processor.IssueProcessor;
import com.koval.resolver.common.api.configuration.Configuration;
import com.koval.resolver.processor.rules.core.RuleEngine;
import com.koval.resolver.processor.rules.core.impl.DroolsRuleEngine;


public class RuleEngineProcessor implements IssueProcessor {

  private final RuleEngine ruleEngine;

  public RuleEngineProcessor(final Configuration configuration) throws IOException {
    this.ruleEngine = new DroolsRuleEngine(configuration.getProcessors().getRuleEngine().getRulesLocation());
  }

  @Override
  public void run(final Issue issue, final IssueAnalysingResult result) {
    setOriginalIssueToResults(issue, result);
    result.setProposals(ruleEngine.execute(issue));
  }
}
