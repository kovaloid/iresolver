package com.koval.resolver.common.api.component.reporter;

import java.util.List;

import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;


public interface ReportGenerator {

  void generate(List<IssueAnalysingResult> results);
}
