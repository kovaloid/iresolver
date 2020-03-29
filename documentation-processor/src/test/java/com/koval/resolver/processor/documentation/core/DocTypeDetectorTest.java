package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.processor.documentation.bean.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DocTypeDetectorTest {
    private static final String PDF_EXTENSION = "pdf";

    private static final String DOC_EXTENSION = "doc";
    private static final String DOCX_EXTENSION = "docx";

    private static final String INCORRECT_EXTENSION = "zzzz";

    private DocTypeDetector mDocTypeDetector;

    @BeforeEach
    void onSetup() {
        mDocTypeDetector = new DocTypeDetector();
    }

    @Test
    void testTrue() {
        assertTrue(true);
    }

    @Test
    void testDetectPdf() {
        MediaType detectedType = mDocTypeDetector.detectType("." + PDF_EXTENSION);

        assertEquals(MediaType.PDF, detectedType);
    }

}