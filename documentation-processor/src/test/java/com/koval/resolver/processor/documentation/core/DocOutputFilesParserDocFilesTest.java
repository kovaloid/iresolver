package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.processor.documentation.bean.DocFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//TODO: This and other DocOutputFilesParser test will test different classes after refactoring
@ExtendWith(MockitoExtension.class)
public class DocOutputFilesParserDocFilesTest {
  private static final String FILE_NAME = "FILE_NAME";

  private static final DocFile DOC_FILE_1 = new DocFile(15, "filename1");
  private static final String DOC_FILE_STRING_1 = "15 filename1";

  private static final DocFile DOC_FILE_2 = new DocFile(26, "filename2");
  private static final String DOC_FILE_STRING_2 = "26 filename2";

  private static final String BOTH_FILES_STRING = DOC_FILE_STRING_1 + "\n" + DOC_FILE_STRING_2;

  private static final String INVALID_STRING_ONLY_NUMBER = "5";
  private static final String INVALID_STRING_ONLY_CHARS = "a\nb";

  @Mock
  private DocFileRepository mDocFileRepository;

  private DocOutputFilesParser mDocOutputFilesParser;

  @BeforeEach
  void onSetup() throws IOException {
    initDocOutputFileParser(BOTH_FILES_STRING);
  }

  @Test
  void testParsingOneLineDocMeta() {
    DocFile actualDocFile = mDocOutputFilesParser.parseDocumentationFilesList().get(0);

    assertDocFilesEqual(DOC_FILE_1, actualDocFile);
  }

  @Test
  void testParsingMultipleDocMeta() {
    List<DocFile> docFiles = mDocOutputFilesParser.parseDocumentationFilesList();

    assertDocFilesEqual(DOC_FILE_1, docFiles.get(0));
    assertDocFilesEqual(DOC_FILE_2, docFiles.get(1));
  }

  //TODO: make this test pass
  @Test
  void testParsingInvalidStringOnlyChars() throws IOException {
    initDocOutputFileParser(INVALID_STRING_ONLY_CHARS);

    mDocOutputFilesParser.parseDocumentationFilesList();
  }

  //TODO: make this test pass
  @Test
  void testParsingInvalidStringOnlyNumber() throws IOException {
    initDocOutputFileParser(INVALID_STRING_ONLY_NUMBER);

    mDocOutputFilesParser.parseDocumentationFilesList();
  }

  private void initDocOutputFileParser(String testString) throws IOException {
    DocumentationProcessorConfiguration properties = createProperties(testString);

    mDocOutputFilesParser = new DocOutputFilesParser(
            properties,
            mDocFileRepository
    );
  }

  private DocumentationProcessorConfiguration createProperties(String testString) throws IOException {
    InputStream inputStream = new ByteArrayInputStream(testString.getBytes());
    when(mDocFileRepository.getFile(FILE_NAME)).thenReturn(inputStream);

    DocumentationProcessorConfiguration properties = new DocumentationProcessorConfiguration();
    properties.setDocsListFile(FILE_NAME);

    return properties;
  }

  private void assertDocFilesEqual(DocFile expectedDocFile, DocFile actualDocFile) {
    assertEquals(expectedDocFile.getFileIndex(), actualDocFile.getFileIndex());
    assertEquals(expectedDocFile.getFileName(), actualDocFile.getFileName());
  }
}
