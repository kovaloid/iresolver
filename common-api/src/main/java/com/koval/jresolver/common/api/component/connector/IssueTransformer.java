package com.koval.jresolver.common.api.component.connector;

import java.util.Collection;
import java.util.List;

import com.koval.jresolver.common.api.bean.issue.Issue;


public interface IssueTransformer<T> {

  Issue transform(T originalIssue);

  List<Issue> transform(Collection<T> originalIssues);
}
