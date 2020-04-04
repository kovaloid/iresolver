package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.processor.documentation.bean.DocFile;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO: This and other DocOutputFilesParser test will test different classes after refactoring
public class DocOutputFilesParserDocFilesTest {
  private static final DocFile DOC_FILE_1 = new DocFile(15, "filename1");
  private static final String DOC_FILE_STRING_1 = "15 filename1\n";

  private static final DocFile DOC_FILE_2 = new DocFile(26, "filename2");
  private static final String DOC_FILE_STRING_2 = "26 filename2";

  private static final List<String> DOC_FILE_STRINGS = Arrays.asList(DOC_FILE_STRING_1, DOC_FILE_STRING_2);

  @TempDir
  File tempDirectory;

  private File tempFile;

  private DocOutputFilesParser mDocOutputFilesParser;

  @BeforeEach
  void onSetup() throws IOException {
    initDocOutputFileParser(DOC_FILE_STRINGS);
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
  void testParsingInvalidCharacterString() throws IOException {
    initDocOutputFileParser(Arrays.asList("a", "b"));

    List<DocFile> actualDocFile = mDocOutputFilesParser.parseDocumentationFilesList();
  }

  //TODO: make this test pass
  @Test
  void testParsingInvalidCharacterStringg() throws IOException {
    initDocOutputFileParser(Arrays.asList("5"));

    List<DocFile> actualDocFile = mDocOutputFilesParser.parseDocumentationFilesList();
  }

  private DocumentationProcessorConfiguration createProperties(List<String> metadataStrings) throws IOException {
    final File tempFile = writeStringsToFile(metadataStrings);

    DocumentationProcessorConfiguration properties = new DocumentationProcessorConfiguration();
    properties.setDocsListFile(tempFile.getPath());

    return properties;
  }

  private void assertDocFilesEqual(DocFile expectedDocFile, DocFile actualDocFile) {
    assertEquals(expectedDocFile.getFileIndex(), actualDocFile.getFileIndex());
    assertEquals(expectedDocFile.getFileName(), actualDocFile.getFileName());
  }

  private File writeStringsToFile(List<String> metadataStrings) throws IOException {
    for (String metadataString : metadataStrings) {
      FileUtils.writeStringToFile(tempFile, metadataString, Charset.defaultCharset(), true);
    }

    return tempFile;
  }

  private void initDocOutputFileParser(List<String> docFileStrings) throws IOException {
    if(tempFile != null) {
      tempFile.delete();
    }

    tempFile = new File(tempDirectory, "tempFile.txt");

    DocumentationProcessorConfiguration properties = createProperties(docFileStrings);

    mDocOutputFilesParser = new DocOutputFilesParser(properties);
  }
}
