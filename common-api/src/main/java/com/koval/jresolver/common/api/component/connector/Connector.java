package com.koval.jresolver.common.api.component.connector;


public interface Connector {

  IssueReceiver getResolvedIssuesReceiver();
  IssueReceiver getUnresolvedIssuesReceiver();
}
