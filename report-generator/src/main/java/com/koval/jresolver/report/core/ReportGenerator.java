package com.koval.jresolver.report.core;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.report.core.impl.HtmlReportGenerator;


public interface ReportGenerator {

  static boolean checkConfigure() {
    return HtmlReportGenerator.checkConfigure();
  }

  void generate(List<JiraIssue> actualIssues, String vectorModelResource) throws IOException, URISyntaxException;

  void configure();
}
