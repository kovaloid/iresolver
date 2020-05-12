package com.koval.resolver.processor.documentation.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.koval.resolver.processor.documentation.bean.DocFile;

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
  private FileRepository fileRepository;

  private DocFileDataParser docFileDataParser;

  @BeforeEach
  void onSetup() throws IOException {
    initDocOutputFileParser(BOTH_FILES_STRING);
  }

  @Test
  void testParsingOneLineDocMeta() {
    final DocFile actualDocFile = docFileDataParser.parseDocumentationFilesList().get(0);

    assertDocFilesEqual(DOC_FILE_1, actualDocFile);
  }

  @Test
  void testParsingMultipleDocMeta() {
    final List<DocFile> docFiles = docFileDataParser.parseDocumentationFilesList();

    assertDocFilesEqual(DOC_FILE_1, docFiles.get(0));
    assertDocFilesEqual(DOC_FILE_2, docFiles.get(1));
  }

  @Disabled("Handle error when string is without file index")
  @Test
  void testParsingInvalidStringOnlyChars() throws IOException {
    initDocOutputFileParser(INVALID_STRING_ONLY_CHARS);

    docFileDataParser.parseDocumentationFilesList();
  }


  @Disabled("Handle error when string is without file name")
  @Test
  void testParsingInvalidStringOnlyNumber() throws IOException {
    initDocOutputFileParser(INVALID_STRING_ONLY_NUMBER);

    docFileDataParser.parseDocumentationFilesList();
  }

  private void initDocOutputFileParser(final String testString) throws IOException {
    final InputStream inputStream = new ByteArrayInputStream(testString.getBytes());
    when(fileRepository.readFile(FILE_NAME)).thenReturn(inputStream);

    docFileDataParser = new DocFileDataParser(
            FILE_NAME,
            fileRepository
    );
  }

  private void assertDocFilesEqual(final DocFile expectedDocFile, final DocFile actualDocFile) {
    assertEquals(expectedDocFile.getFileIndex(), actualDocFile.getFileIndex());
    assertEquals(expectedDocFile.getFileName(), actualDocFile.getFileName());
  }

  private static String constructTestString(final DocFile docFile) {
    return docFile.getFileIndex() + DELIMITER + docFile.getFileName();
  }
}
