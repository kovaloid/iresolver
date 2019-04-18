package com.koval.jresolver.processor.rules;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.jresolver.common.api.component.processor.IssueProcessor;
import com.koval.jresolver.processor.rules.core.RuleEngine;


public class RuleEngineProcessor implements IssueProcessor {

  private final RuleEngine ruleEngine;

  public RuleEngineProcessor(RuleEngine ruleEngine) {
    this.ruleEngine = ruleEngine;
  }

  @Override
  public void run(Issue issue, IssueAnalysingResult result) {
    result.setProposals(ruleEngine.execute(issue));
  }
}
