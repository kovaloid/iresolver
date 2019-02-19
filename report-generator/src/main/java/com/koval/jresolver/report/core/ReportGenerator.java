package com.koval.jresolver.report.core;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import com.atlassian.jira.rest.client.api.domain.Issue;


public interface ReportGenerator {

  void generate(Collection<Issue> actualIssues) throws IOException, URISyntaxException;

  void configure();
}
