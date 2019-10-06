package com.koval.jresolver.docprocessor.configuration;

public class DocumentationProcessorProperties {

    private String filename;

    public DocumentationProcessorProperties() {
        this.filename = "test.pdf";
    }

    public String getFilename() {
        return this.filename;
    }
}
