package com.koval.jresolver.common.api.component;

import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;

import java.util.List;


public interface ReportGenerator {

  void generate(List<IssueAnalysingResult> results);
}
