package com.koval.resolver.common.api.component.connector;


public interface Connector {

  IssueReceiver getResolvedIssuesReceiver();

  IssueReceiver getUnresolvedIssuesReceiver();
}
