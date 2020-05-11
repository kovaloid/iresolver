package util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;

public final class ConfigurationPropertiesCreator {

  private ConfigurationPropertiesCreator() {

  }

  public static DocumentationProcessorConfiguration createProperties(
          List<String> metadataStrings,
          File docFile,
          File datasetFile,
          File metadataFile,
          File docListFile
  ) throws IOException {
    final File tempFile = writeStringsToFile(metadataStrings, docFile);
    DocumentationProcessorConfiguration properties = new DocumentationProcessorConfiguration();
    properties.setDocsFolder(tempFile.getParent());

    properties.setDataSetFile(datasetFile.getPath());
    properties.setDocsMetadataFile(metadataFile.getPath());
    properties.setDocsListFile(docListFile.getPath());

    return properties;
  }

  static File writeStringsToFile(
          List<String> metadataStrings,
          File file
  ) throws IOException {
    for (String metadataString : metadataStrings) {
      FileUtils.writeStringToFile(file, metadataString, Charset.defaultCharset(), true);
    }

    return file;
  }
}
