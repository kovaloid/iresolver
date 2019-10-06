package com.koval.jresolver.docprocessor;

import com.koval.jresolver.common.api.component.connector.Connector;
import com.koval.jresolver.common.api.component.connector.IssueReceiver;
import com.koval.jresolver.docprocessor.configuration.DocumentationProcessorProperties;
import com.koval.jresolver.docprocessor.core.DocumentationParagraphReceiver;

public class DocumentationProcessor implements Connector {

    private DocumentationProcessorProperties properties;

    public DocumentationProcessor(DocumentationProcessorProperties properties) {
        this.properties = properties;
    }

    @Override
    public IssueReceiver getResolvedIssuesReceiver() {
        return null;
    }

    @Override
    public IssueReceiver getUnresolvedIssuesReceiver() {
        return null;
    }

}
