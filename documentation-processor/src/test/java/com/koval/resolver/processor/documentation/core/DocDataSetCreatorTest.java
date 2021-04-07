package com.koval.resolver.processor.documentation.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.koval.resolver.common.api.configuration.ConfigurationManager;
import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.processor.documentation.bean.MediaType;
import com.koval.resolver.processor.documentation.convert.FileConverter;
import com.koval.resolver.processor.documentation.convert.impl.HtmlToPdfFileConverter;
import com.koval.resolver.processor.documentation.convert.impl.PptPptxToPdfFileConverter;
import com.koval.resolver.processor.documentation.convert.impl.WordToPdfFileConverter;
import com.koval.resolver.processor.documentation.convert.impl.XwpfPdfConverter;


////TODO: Refactor DocDataSetCreator to get rid of real files in this test
@ExtendWith(MockitoExtension.class)
public class DocDataSetCreatorTest {

//  private static final String FILE_NAME = "tempfile.txt";
//  private static final String DATA_SET_FILE_NAME = "dataset.txt";
//  private static final String METADATA_FILE_NAME = "metadata.txt";
//  private static final String DOC_LIST_FILE_NAME = "doclist.txt";
//
//  private static final String DOC_FILE_STRING_1 = "15 filename1\n";
//  private static final String DOC_FILE_STRING_2 = "26 filename2";
//
//  private static final List<String> DOC_FILE_STRINGS = Arrays.asList(DOC_FILE_STRING_1, DOC_FILE_STRING_2);

    @Mock
    private DocDataSetEntryWriter docDataSetEntryWriter;

    @Mock
    private DocTypeDetector docTypeDetector;

//    @Mock
//    private FileConverter fileConverter;

    private DocDataSetCreator docDataSetCreator;

    private DocumentationProcessorConfiguration properties;

    //@TempDir
    //@SuppressWarnings("checkstyle:visibilitymodifier")
    //File tempDirectory;

    //private File docFile;

    @BeforeEach
    void onSetup() throws IOException {
       properties = ConfigurationManager
               .getConfiguration()
               .getProcessors()
               .getDocumentation();
       docDataSetCreator = new DocDataSetCreator(
               properties,
               docDataSetEntryWriter,
               docTypeDetector,
               createAllFileConverter()
        );
    }

    @Test
    void checkDocsDirectory() throws IOException {
        Assertions.assertEquals(properties.getDocsFolder(), docDataSetCreator.getDocsFolderPath());
    }

//
//  @Test
//  void testCreateFromPdf() throws IOException {
//      when(docTypeDetector.detectType(FILE_NAME)).thenReturn(MediaType.PDF);
//  }
////
////    docDataSetCreator.create();
////
////    verifyWriteEntriesCalled();
////  }
////
////  private void verifyWriteEntriesCalled() throws IOException {
////    verify(docDataSetEntryWriter)
////            .writeEntriesForDocFile(
////                    eq(docFile),
////                    any(BufferedWriter.class),
////                    any(BufferedWriter.class),
////                    any(BufferedWriter.class)
////            );
//  }
//
//  @Test
//  void testConvertingDocFile() {
////    when(docTypeDetector.detectType(FILE_NAME)).thenReturn(MediaType.WORD);
////
////    docDataSetCreator.convertWordFilesToPdf();
////
////    verify(fileConverter).convert(eq(docFile.getName()), anyString());
//  }
//
//  @Test
//  void testNotConvertingOtherExtension() {
////    when(docTypeDetector.detectType(FILE_NAME)).thenReturn(MediaType.UNKNOWN);
////
////    docDataSetCreator.convertWordFilesToPdf();
////
////    verify(fileConverter, never()).convert(eq(docFile.getName()), anyString());
//  }
//
//  @Test
//  void testHandlingInvalidDirectory() {
////    docFile.delete();
////    tempDirectory.delete();
////
////    docDataSetCreator.convertWordFilesToPdf();
////
////    verify(fileConverter, never()).convert(eq(docFile.getName()), anyString());
//  }
//
//  @Test
//  void testHandlingOnlyDirectories() {
////    docFile.delete();
////    new File(tempDirectory, FILE_NAME).mkdir();
////
//////    docDataSetCreator.convertWordFilesToPdf();
////
////    verify(fileConverter, never()).convert(eq(docFile.getName()), anyString());
//  }
    private Map<MediaType, FileConverter> createAllFileConverter() {
        HashMap<MediaType, FileConverter> fileConverters = new HashMap<>();
        final FileRepository fileRepository = new FileRepository();
        final XwpfPdfConverter pdfConverter = new XwpfPdfConverter();
        fileConverters.put(MediaType.WORD, new WordToPdfFileConverter(
                fileRepository,
                pdfConverter
                )
        );
        fileConverters.put(MediaType.POWERPOINT, new PptPptxToPdfFileConverter(fileRepository));
        fileConverters.put(MediaType.HTML, new HtmlToPdfFileConverter());
        return fileConverters;
    }

  /*
    private DocumentationProcessorConfiguration createConfigurationProperties() throws IOException {
        if (docFile != null) {
            docFile.delete();
        }

        docFile = new File(tempDirectory, FILE_NAME);
        final File dataSetFile = new File(tempDirectory, DATA_SET_FILE_NAME);
        final File metadataFile = new File(tempDirectory, METADATA_FILE_NAME);
        final File docListFile = new File(tempDirectory, DOC_LIST_FILE_NAME);
        return createProperties(DOC_FILE_STRINGS, docFile, dataSetFile, metadataFile, docListFile);
  }
 */
}
