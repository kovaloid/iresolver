package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.processor.documentation.bean.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DocTypeDetectorTest {
    private static final String FILE_NAME = "filename";

    private static final String PDF_EXTENSION = "pdf";

    private static final String DOC_EXTENSION = "doc";
    private static final String DOCX_EXTENSION = "docx";

    private static final String UNKNOWN_EXTENSION = "zzzz";

    private static final String EXTENSION_DELIMITER = ".";

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
        MediaType detectedType = detectType(PDF_EXTENSION);

        assertEquals(MediaType.PDF, detectedType);
    }

    @Test
    void testDetectWordFromDoc() {
        MediaType detectedType = detectType(DOC_EXTENSION);

        assertEquals(MediaType.WORD, detectedType);
    }

    @Test
    void testDetectWordFromDocx() {
        MediaType detectedType = detectType(DOCX_EXTENSION);

        assertEquals(MediaType.WORD, detectedType);
    }

    @Test
    void testDetectUnknownExtension() {
        MediaType detectedType = detectType(UNKNOWN_EXTENSION);

        assertEquals(MediaType.UNKNOWN, detectedType);
    }

    private MediaType detectType(String fileExtension) {
        String fileName = FILE_NAME + EXTENSION_DELIMITER + fileExtension;
        return mDocTypeDetector.detectType(fileName);
    }

}