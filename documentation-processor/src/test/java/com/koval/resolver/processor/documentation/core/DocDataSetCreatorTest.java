package com.koval.resolver.processor.documentation.core;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.processor.documentation.bean.MediaType;
import com.koval.resolver.processor.documentation.convert.FileConverter;

import static util.ConfigurationPropertiesCreator.createProperties;

//TODO: Refactor DocDataSetCreator to get rid of real files in this test
@ExtendWith(MockitoExtension.class)
public class DocDataSetCreatorTest {

  private static final String FILE_NAME = "tempfile.txt";

  private static final String DOC_FILE_STRING_1 = "15 filename1\n";
  private static final String DOC_FILE_STRING_2 = "26 filename2";

  private static final List<String> DOC_FILE_STRINGS = Arrays.asList(DOC_FILE_STRING_1, DOC_FILE_STRING_2);

  @Mock
  private DocDataSetEntryWriter docDataSetEntryWriter;

  @Mock
  private DocTypeDetector docTypeDetector;

  @Mock
  private FileConverter fileConverter;

  private DocDataSetCreator docDataSetCreator;

  @TempDir
  @SuppressWarnings("checkstyle:visibilitymodifier")
  File tempDirectory;

  private File tempFile;

  @BeforeEach
  void onSetup() throws IOException {
    DocumentationProcessorConfiguration properties = createConfigurationProperties();
    docDataSetCreator = new DocDataSetCreator(
            properties,
            docDataSetEntryWriter,
            docTypeDetector,
            fileConverter
    );
  }

  @Test
  void testConvertingDocFile() {
    when(docTypeDetector.detectType(FILE_NAME)).thenReturn(MediaType.WORD);

    docDataSetCreator.convertWordFilesToPdf();

    verify(fileConverter).convert(eq(tempFile.getName()), anyString());
  }

  @Test
  void testConvertingOtherExtension() {
    when(docTypeDetector.detectType(FILE_NAME)).thenReturn(MediaType.UNKNOWN);

    docDataSetCreator.convertWordFilesToPdf();

    verify(fileConverter, never()).convert(eq(tempFile.getName()), anyString());
  }

  private DocumentationProcessorConfiguration createConfigurationProperties() throws IOException {
    if (tempFile != null) {
      tempFile.delete();
    }

    tempFile = new File(tempDirectory, FILE_NAME);

    return createProperties(DOC_FILE_STRINGS, tempFile);
  }
}
