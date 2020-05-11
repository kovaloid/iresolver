package com.koval.resolver.processor.documentation.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.koval.resolver.processor.documentation.split.PageSplitter;

//TODO: This test knows too much implementation details
@ExtendWith(MockitoExtension.class)
class DocDataSetEntryWriterTest {

  private static final String FILE_NAME_1 = "filename1";
  private static final String FILE_NAME_2 = "filename2";

  private static final String DELIMITER = " ";

  private static final String DATA = "bla bla bla\n tralalal tralala";

  private static final Map<Integer, String> MAPPINGS = Map.of(
          1, "lol",
          2, "kek",
          3, "cheburek"
  );

  private static final Map<Integer, String> ONE_PAGE_MAPPING = Map.of(
          0, "lol"
  );

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
  void setUp() throws IOException {
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

    docDataSetEntryWriter.writeEntriesForDocFile(
            docFile,
            mockWriter,
            mockWriter,
            mockWriter
    );

    verify(docListFileEntryWriter).write(mockWriter, 0, FILE_NAME_1, DELIMITER);
  }

  @Test
  void testChangingFileIndexForDocFileEntry() throws IOException {
    doReturn(ONE_PAGE_MAPPING).when(pageSplitter).getMapping(any(InputStream.class));
    when(docFile.getName()).thenReturn(FILE_NAME_1);

    docDataSetEntryWriter.writeEntriesForDocFile(
            docFile,
            mockWriter,
            mockWriter,
            mockWriter
    );

    verify(docListFileEntryWriter).write(mockWriter, 0, FILE_NAME_1, DELIMITER);

    when(docFile.getName()).thenReturn(FILE_NAME_2);
    docDataSetEntryWriter.writeEntriesForDocFile(
            docFile,
            mockWriter,
            mockWriter,
            mockWriter
    );

    verify(docListFileEntryWriter).write(mockWriter, 1, FILE_NAME_2, DELIMITER);
  }

  @Test
  void testWritingMetadataEntry() throws IOException {
    doReturn(ONE_PAGE_MAPPING).when(pageSplitter).getMapping(any(InputStream.class));
    docDataSetEntryWriter.writeEntriesForDocFile(
            docFile,
            mockWriter,
            mockWriter,
            mockWriter
    );
    verify(metadataFileEntryWriter).write(mockWriter, "doc_0", 0, 0, DELIMITER);
  }

  @Test
  void testWritingMetadataTwoEntries() throws IOException {
    HashMap<Integer, String> map = new HashMap<>();
    map.put(0, "lol");
    map.put(1, "kek");
    doReturn(map).when(pageSplitter).getMapping(any(InputStream.class));

    docDataSetEntryWriter.writeEntriesForDocFile(
            docFile,
            mockWriter,
            mockWriter,
            mockWriter
    );
    verify(metadataFileEntryWriter).write(mockWriter, "doc_0", 0, 0, DELIMITER);
    verify(metadataFileEntryWriter).write(mockWriter, "doc_1", 0, 1, DELIMITER);
  }

  @Test
  void testWritingMetadataChangingDocumentIndex() throws IOException {
    HashMap<Integer, String> map = new HashMap<>();
    map.put(0, "lol");

    HashMap<Integer, String> map2 = new HashMap<>();
    map2.put(0, "kek");

    when(pageSplitter.getMapping(any(InputStream.class))).thenReturn(map).thenReturn(map2);

    docDataSetEntryWriter.writeEntriesForDocFile(
            docFile,
            mockWriter,
            mockWriter,
            mockWriter
    );
    verify(metadataFileEntryWriter).write(mockWriter, "doc_0", 0, 0, DELIMITER);

    docDataSetEntryWriter.writeEntriesForDocFile(
            docFile,
            mockWriter,
            mockWriter,
            mockWriter
    );
    verify(metadataFileEntryWriter).write(mockWriter, "doc_1", 1, 0, DELIMITER);
  }

}
