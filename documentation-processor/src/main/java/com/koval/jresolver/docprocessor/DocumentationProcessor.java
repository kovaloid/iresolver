package com.koval.jresolver.docprocessor;

import com.koval.jresolver.common.api.component.connector.Connector;
import com.koval.jresolver.common.api.component.connector.IssueReceiver;

public class DocumentationProcessor implements Connector {

    @Override
    public IssueReceiver getResolvedIssuesReceiver() {
        return null;
    }

    @Override
    public IssueReceiver getUnresolvedIssuesReceiver() {
        return null;
    }
}
