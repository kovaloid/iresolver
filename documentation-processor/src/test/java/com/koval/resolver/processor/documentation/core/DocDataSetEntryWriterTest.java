package com.koval.resolver.processor.documentation.core;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.koval.resolver.processor.documentation.split.PageSplitter;

//TODO: This test knows too much implementation details,
// we need to extract each entry parsing into separate classes
@ExtendWith(MockitoExtension.class)
class DocDataSetEntryWriterTest {

  private static final String FILE_NAME = "filename";

  private static final String DATA = "bla bla bla\n tralalal tralala";

  private static final Map<Integer, String> MAPPINGS = Map.of(
          1, "lol",
          2, "kek",
          3, "cheburek"
  );

  @Mock
  private File docFile;

  @Mock
  private FileRepository fileRepository;

  @Mock
  private PageSplitter pageSplitter;

  private DocDataSetEntryWriter docDataSetEntryWriter;

  @BeforeEach
  void setUp() {
    docDataSetEntryWriter = new DocDataSetEntryWriter(
            fileRepository,
            pageSplitter
    );
  }

  @Test
  void testWriteEntriesForDocFile() throws IOException {
    String charset = StandardCharsets.UTF_8.name();

    doReturn(FILE_NAME).when(docFile).getName();

    InputStream inputStream = new ByteArrayInputStream(DATA.getBytes());
    doReturn(inputStream).when(fileRepository).readFile(FILE_NAME);
    doReturn(MAPPINGS).when(pageSplitter).getMapping(any(InputStream.class));

    StringWriter dataSetStringWriter = new StringWriter();
    BufferedWriter dataSetWriter = new BufferedWriter(dataSetStringWriter);

    StringWriter metadataStringWriter = new StringWriter();
    BufferedWriter metadataWriter = new BufferedWriter(metadataStringWriter);

    StringWriter docListStringWriter = new StringWriter();
    BufferedWriter docListWriter = new BufferedWriter(docListStringWriter);

    docDataSetEntryWriter.writeEntriesForDocFile(
            docFile,
            dataSetWriter,
            metadataWriter,
            docListWriter
    );

    dataSetWriter.flush();
    metadataWriter.flush();
    docListWriter.flush();

    String dataSetEntry = dataSetStringWriter.toString();
    String metadataEntry = metadataStringWriter.toString();
    String docListEntry = docListStringWriter.toString();

    assertEquals(docListEntry, "0 " + FILE_NAME + System.lineSeparator());

  }
}
