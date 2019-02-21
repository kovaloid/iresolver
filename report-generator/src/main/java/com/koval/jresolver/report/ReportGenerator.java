package com.koval.jresolver.report;

import java.util.Collection;

import com.koval.jresolver.processor.result.IssueProcessingResult;


public interface ReportGenerator {

  void generate(Collection<IssueProcessingResult> results);
}
