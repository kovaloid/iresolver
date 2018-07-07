package com.koval.jresolver.report.core;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.koval.jresolver.connector.bean.JiraIssue;


public interface ReportGenerator {

  void generate(List<JiraIssue> actualIssues) throws IOException, URISyntaxException;

  void configure();
}
