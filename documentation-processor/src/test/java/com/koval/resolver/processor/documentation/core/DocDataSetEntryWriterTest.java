package com.koval.resolver.processor.documentation.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.koval.resolver.processor.documentation.split.PageSplitter;

//TODO: This test knows too much implementation details
@ExtendWith(MockitoExtension.class)
class DocDataSetEntryWriterTest {

  private static final String FILE_NAME_1 = "filename1";
  private static final String FILE_NAME_2 = "filename2";

  private static final String DELIMITER = " ";
  private static final String DATA_SET_SEPARATOR = "|";

  private static final String FIRST_PAGE_KEY = "doc_0";
  private static final String SECOND_PAGE_KEY = "doc_1";

  private static final int FIRST_DOCUMENT_INDEX = 0;
  private static final int FIRST_PAGE_NUMBER = 0;
  private static final String FIRST_PAGE_TEXT = "first page text";

  private static final int SECOND_DOCUMENT_INDEX = 1;
  private static final int SECOND_PAGE_NUMBER = 1;
  private static final String SECOND_PAGE_TEXT = "second page text";

  private static final Map<Integer, String> ONE_PAGE_MAPPING = createOnePageMapping();

  private static final Map<Integer, String> TWO_PAGE_MAPPING = createTwoPageMapping();

  private static Map<Integer, String> createOnePageMapping() {
    final Map<Integer, String> map = new HashMap<>();

    map.put(FIRST_PAGE_NUMBER, FIRST_PAGE_TEXT);

    return map;
  }

  private static Map<Integer, String> createTwoPageMapping() {
    final Map<Integer, String> map = new HashMap<>();

    map.put(FIRST_PAGE_NUMBER, FIRST_PAGE_TEXT);
    map.put(SECOND_PAGE_NUMBER, SECOND_PAGE_TEXT);

    return map;
  }

  @Mock
  private File docFile;

  @Mock
  private FileRepository fileRepository;

  @Mock
  private PageSplitter pageSplitter;

  @Mock
  private MetadataFileEntryWriter metadataFileEntryWriter;

  @Mock
  private DocListFileEntryWriter docListFileEntryWriter;

  @Mock
  private DataSetFileEntryWriter dataSetFileEntryWriter;

  @Mock
  private BufferedWriter mockWriter;

  private DocDataSetEntryWriter docDataSetEntryWriter;

  @BeforeEach
  void onSetup() {
    docDataSetEntryWriter = new DocDataSetEntryWriter(
            fileRepository,
            pageSplitter,
            metadataFileEntryWriter,
            docListFileEntryWriter,
            dataSetFileEntryWriter
    );
  }

  @Test
  void testWriteEntriesForDocFile() throws IOException {
    doReturn(ONE_PAGE_MAPPING).when(pageSplitter).getMapping(any(InputStream.class));
    when(docFile.getName()).thenReturn(FILE_NAME_1);

    writeEntriesForDocFile();

    verify(docListFileEntryWriter).write(mockWriter, FIRST_DOCUMENT_INDEX, FILE_NAME_1, DELIMITER);
  }

  @Test
  void testWritingDatasetFileEntry() throws IOException {
    doReturn(ONE_PAGE_MAPPING).when(pageSplitter).getMapping(any(InputStream.class));

    writeEntriesForDocFile();

    verify(dataSetFileEntryWriter).write(mockWriter, FIRST_PAGE_KEY, FIRST_PAGE_TEXT, DATA_SET_SEPARATOR);
  }

  @Test
  void testWritingDatasetTwoFileEntries() throws IOException {
    doReturn(TWO_PAGE_MAPPING).when(pageSplitter).getMapping(any(InputStream.class));

    writeEntriesForDocFile();

    verify(dataSetFileEntryWriter).write(mockWriter, FIRST_PAGE_KEY, FIRST_PAGE_TEXT, DATA_SET_SEPARATOR);
    verify(dataSetFileEntryWriter).write(mockWriter, SECOND_PAGE_KEY, SECOND_PAGE_TEXT, DATA_SET_SEPARATOR);
  }

  @Test
  void testChangingFileIndexForDocFileEntry() throws IOException {
    doReturn(ONE_PAGE_MAPPING).when(pageSplitter).getMapping(any(InputStream.class));
    when(docFile.getName()).thenReturn(FILE_NAME_1);

    writeEntriesForDocFile();

    verify(docListFileEntryWriter).write(mockWriter, FIRST_DOCUMENT_INDEX, FILE_NAME_1, DELIMITER);

    when(docFile.getName()).thenReturn(FILE_NAME_2);
    writeEntriesForDocFile();

    verify(docListFileEntryWriter).write(mockWriter, SECOND_DOCUMENT_INDEX, FILE_NAME_2, DELIMITER);
  }

  @Test
  void testWritingMetadataEntry() throws IOException {
    doReturn(ONE_PAGE_MAPPING).when(pageSplitter).getMapping(any(InputStream.class));

    writeEntriesForDocFile();

    verifyMetadataEntryWritten(FIRST_PAGE_KEY, FIRST_DOCUMENT_INDEX, FIRST_PAGE_NUMBER);
  }

  @Test
  void testWritingMetadataTwoEntries() throws IOException {
    doReturn(TWO_PAGE_MAPPING).when(pageSplitter).getMapping(any(InputStream.class));

    writeEntriesForDocFile();

    verifyMetadataEntryWritten(FIRST_PAGE_KEY, FIRST_DOCUMENT_INDEX, FIRST_PAGE_NUMBER);
    verifyMetadataEntryWritten(SECOND_PAGE_KEY, FIRST_DOCUMENT_INDEX, SECOND_PAGE_NUMBER);
  }

  @Test
  void testWritingMetadataChangingDocumentIndex() throws IOException {
    final HashMap<Integer, String> map = new HashMap<>();
    map.put(0, "lol");

    final HashMap<Integer, String> map2 = new HashMap<>();
    map2.put(0, "kek");

    when(pageSplitter.getMapping(any(InputStream.class))).thenReturn(map).thenReturn(map2);

    writeEntriesForDocFile();
    verifyMetadataEntryWritten(FIRST_PAGE_KEY, FIRST_DOCUMENT_INDEX, FIRST_PAGE_NUMBER);

    writeEntriesForDocFile();
    verifyMetadataEntryWritten(SECOND_PAGE_KEY, SECOND_DOCUMENT_INDEX, FIRST_PAGE_NUMBER);
  }

  @Test
  void testResettingIndices() throws IOException {
    doReturn(ONE_PAGE_MAPPING).when(pageSplitter).getMapping(any(InputStream.class));

    writeEntriesForDocFile();
    writeEntriesForDocFile();

    docDataSetEntryWriter.resetIndices();

    writeEntriesForDocFile();

    verifyLastMetadataEntryCall();
  }

  private void writeEntriesForDocFile() throws IOException {
    docDataSetEntryWriter.writeEntriesForDocFile(
            docFile,
            mockWriter,
            mockWriter,
            mockWriter
    );
  }

  private void verifyMetadataEntryWritten(
          String expectedPageKey,
          int expectedDocumentIndex,
          int expectedPageNumber
  ) throws IOException {
    verify(metadataFileEntryWriter).write(
            mockWriter,
            expectedPageKey,
            expectedDocumentIndex,
            expectedPageNumber,
            DELIMITER
    );
  }

  private void verifyLastMetadataEntryCall() throws IOException {
    final ArgumentCaptor<String> pageKeyCaptor = ArgumentCaptor.forClass(String.class);

    verify(metadataFileEntryWriter, atLeastOnce()).write(
            eq(mockWriter),
            pageKeyCaptor.capture(),
            eq(FIRST_DOCUMENT_INDEX),
            eq(FIRST_PAGE_NUMBER),
            eq(DELIMITER)
    );

    assertEquals(FIRST_PAGE_KEY, pageKeyCaptor.getValue());
  }

}
