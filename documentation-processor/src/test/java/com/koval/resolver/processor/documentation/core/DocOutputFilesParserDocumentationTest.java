package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.processor.documentation.bean.DocMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//TODO: This and other DocOutputFilesParser test will test different classes after refactoring
@ExtendWith(MockitoExtension.class)
class DocOutputFilesParserDocumentationTest {
  private static final DocMetadata DOC_METADATA_1 = new DocMetadata("key1", 14, 23);
  private static final String METADATA_STRING_1 = "key1 14 23\n";

  private static final DocMetadata DOC_METADATA_2 = new DocMetadata("key2", 25, 67);
  private static final String METADATA_STRING_2 = "key2 25 67";

  private static final String METADATA_STRINGS = METADATA_STRING_1 + METADATA_STRING_2;

  private static final String FILE_NAME = "tempFile.txt";

  @Mock
  DocFileRepository mDocFileRepository;

  private DocOutputFilesParser mDocOutputFilesParser;

  @BeforeEach
  void onSetup() throws IOException {
    MockitoAnnotations.initMocks(this);

    DocumentationProcessorConfiguration properties = createProperties(METADATA_STRINGS);

    mDocOutputFilesParser = new DocOutputFilesParser(
            properties,
            mDocFileRepository
    );
  }

  @Test
  void testParsingOneLineDocMeta() {
    DocMetadata actualMetaData = mDocOutputFilesParser.parseDocumentationMetadata().get(0);

    assertMetadataEqual(DOC_METADATA_1, actualMetaData);
  }

  @Test
  void testParsingMultipleDocMeta() {
    List<DocMetadata> metadataList = mDocOutputFilesParser.parseDocumentationMetadata();

    assertMetadataEqual(DOC_METADATA_1, metadataList.get(0));
    assertMetadataEqual(DOC_METADATA_2, metadataList.get(1));
  }

  private DocumentationProcessorConfiguration createProperties(String testString) throws IOException {
    InputStream inputStream = new ByteArrayInputStream(testString.getBytes());
    when(mDocFileRepository.getFile(FILE_NAME)).thenReturn(inputStream);

    DocumentationProcessorConfiguration properties = new DocumentationProcessorConfiguration();
    properties.setDocsMetadataFile(FILE_NAME);

    return properties;
  }

  private void assertMetadataEqual(DocMetadata expectedMetaData, DocMetadata actualMetaData) {
    assertEquals(expectedMetaData.getKey(), actualMetaData.getKey());
    assertEquals(expectedMetaData.getFileIndex(), actualMetaData.getFileIndex());
    assertEquals(expectedMetaData.getPageNumber(), actualMetaData.getPageNumber());
  }


}
