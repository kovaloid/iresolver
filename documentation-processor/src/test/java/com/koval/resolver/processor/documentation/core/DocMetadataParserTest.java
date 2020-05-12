package com.koval.resolver.processor.documentation.core;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.koval.resolver.processor.documentation.bean.DocMetadata;

@ExtendWith(MockitoExtension.class)
class DocMetadataParserTest {

  private static final String DELIMITER = " ";

  private static final DocMetadata DOC_METADATA_1 = new DocMetadata("key1", 14, 23);
  private static final String METADATA_STRING_1 = constructTestString(DOC_METADATA_1);

  private static final DocMetadata DOC_METADATA_2 = new DocMetadata("key2", 25, 67);
  private static final String METADATA_STRING_2 = constructTestString(DOC_METADATA_2);

  private static final String METADATA_STRINGS = METADATA_STRING_1 + "\n" + METADATA_STRING_2;

  private static final String FILE_NAME = "tempFile.txt";

  @Mock
  private FileRepository fileRepository;

  private DocMetadataParser docMetadataParser;

  @BeforeEach
  void onSetup() throws FileNotFoundException {
    final InputStream inputStream = new ByteArrayInputStream(METADATA_STRINGS.getBytes());
    when(fileRepository.readFile(FILE_NAME)).thenReturn(inputStream);

    docMetadataParser = new DocMetadataParser(
            FILE_NAME,
            fileRepository
    );
  }

  @Test
  void testParsingOneLineDocMeta() {
    final DocMetadata actualMetaData = docMetadataParser.parseDocumentationMetadata().get(0);

    assertMetadataEqual(DOC_METADATA_1, actualMetaData);
  }

  @Test
  void testParsingMultipleDocMeta() {
    final List<DocMetadata> metadataList = docMetadataParser.parseDocumentationMetadata();

    assertMetadataEqual(DOC_METADATA_1, metadataList.get(0));
    assertMetadataEqual(DOC_METADATA_2, metadataList.get(1));
  }

  private void assertMetadataEqual(final DocMetadata expectedMetaData, final DocMetadata actualMetaData) {
    assertEquals(expectedMetaData.getKey(), actualMetaData.getKey());
    assertEquals(expectedMetaData.getFileIndex(), actualMetaData.getFileIndex());
    assertEquals(expectedMetaData.getPageNumber(), actualMetaData.getPageNumber());
  }

  private static String constructTestString(final DocMetadata docMetadata) {
    return docMetadata.getKey()
            + DELIMITER + docMetadata.getFileIndex()
            + DELIMITER + docMetadata.getPageNumber();
  }
}
