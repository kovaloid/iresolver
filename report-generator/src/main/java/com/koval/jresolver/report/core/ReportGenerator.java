package com.koval.jresolver.report.core;

import java.util.Collection;

import com.koval.jresolver.processor.IssueProcessingResult;


public interface ReportGenerator {

  void generate(Collection<IssueProcessingResult> results);

  void configure();
}
