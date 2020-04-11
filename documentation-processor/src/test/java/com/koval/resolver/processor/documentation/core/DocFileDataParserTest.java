package com.koval.resolver.processor.documentation.core;

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

@ExtendWith(MockitoExtension.class)
public class DocFileDataParserTest {
  private static final String FILE_NAME = "FILE_NAME";
  private static final String DELIMITER = " ";

  private static final DocFile DOC_FILE_1 = new DocFile(15, "filename1");
  private static final String DOC_FILE_STRING_1 = constructTestString(DOC_FILE_1);

  private static final DocFile DOC_FILE_2 = new DocFile(26, "filename2");
  private static final String DOC_FILE_STRING_2 = constructTestString(DOC_FILE_2);

  private static final String BOTH_FILES_STRING = DOC_FILE_STRING_1 + "\n" + DOC_FILE_STRING_2;

  private static final String INVALID_STRING_ONLY_NUMBER = "5";
  private static final String INVALID_STRING_ONLY_CHARS = "a\nb";

  @Mock
  private DocFileRepository mDocFileRepository;

  private DocFileDataParser mDocFileDataParser;

  @BeforeEach
  void onSetup() throws IOException {
    initDocOutputFileParser(BOTH_FILES_STRING);
  }

  @Test
  void testParsingOneLineDocMeta() {
    DocFile actualDocFile = mDocFileDataParser.parseDocumentationFilesList().get(0);

    assertDocFilesEqual(DOC_FILE_1, actualDocFile);
  }

  @Test
  void testParsingMultipleDocMeta() {
    List<DocFile> docFiles = mDocFileDataParser.parseDocumentationFilesList();

    assertDocFilesEqual(DOC_FILE_1, docFiles.get(0));
    assertDocFilesEqual(DOC_FILE_2, docFiles.get(1));
  }

  //TODO: make this test pass
  @Test
  void testParsingInvalidStringOnlyChars() throws IOException {
    initDocOutputFileParser(INVALID_STRING_ONLY_CHARS);

    mDocFileDataParser.parseDocumentationFilesList();
  }

  //TODO: make this test pass
  @Test
  void testParsingInvalidStringOnlyNumber() throws IOException {
    initDocOutputFileParser(INVALID_STRING_ONLY_NUMBER);

    mDocFileDataParser.parseDocumentationFilesList();
  }

  private void initDocOutputFileParser(String testString) throws IOException {
    InputStream inputStream = new ByteArrayInputStream(testString.getBytes());
    when(mDocFileRepository.getFile(FILE_NAME)).thenReturn(inputStream);

    mDocFileDataParser = new DocFileDataParser(
            FILE_NAME,
            mDocFileRepository
    );
  }

  private void assertDocFilesEqual(DocFile expectedDocFile, DocFile actualDocFile) {
    assertEquals(expectedDocFile.getFileIndex(), actualDocFile.getFileIndex());
    assertEquals(expectedDocFile.getFileName(), actualDocFile.getFileName());
  }

  private static String constructTestString(DocFile docFile) {
    return docFile.getFileIndex() + DELIMITER + docFile.getFileName();
  }

}
