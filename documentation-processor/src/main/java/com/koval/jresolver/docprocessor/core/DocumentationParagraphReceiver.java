package com.koval.jresolver.docprocessor.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.koval.jresolver.docprocessor.configuration.DocumentationProcessorProperties;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.component.connector.IssueReceiver;



public class DocumentationParagraphReceiver implements IssueReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentationParagraphReceiver.class);

    private BodyContentHandler handler;
    private AutoDetectParser parser;
    private Metadata metadata;

    public DocumentationParagraphReceiver(DocumentationProcessorProperties properties) {
        this.handler = new BodyContentHandler();
        this.parser =  new AutoDetectParser();
        this.metadata = new Metadata();
        try(InputStream stream = DocumentationParagraphReceiver.class.getResourceAsStream(properties.getFilename())) {
            parser.parse(stream, handler, metadata);
        }
        catch (TikaException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        catch (SAXException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    @Override
    public boolean hasNextIssues() {
        return false;
    }

    @Override
    public List<Issue> getNextIssues() {
        return null;
    }

    public String getNextIssue() {
        return handler.toString();
    }
}
