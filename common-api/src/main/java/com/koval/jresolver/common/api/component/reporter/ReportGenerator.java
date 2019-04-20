package com.koval.jresolver.common.api.component.reporter;

import java.util.List;

import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;


public interface ReportGenerator {

  void generate(List<IssueAnalysingResult> results);
}
