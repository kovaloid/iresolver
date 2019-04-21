package com.koval.jresolver.reporter.html.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.exception.ConfigurationException;


public class HtmlReporterConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(HtmlReporterConfiguration.class);
  private static final String REPORTER_PROPERTIES = "html-reporter.properties";

  private boolean openBrowser = true;
  private String outputFolder = "../output/";

  public HtmlReporterConfiguration() {
    loadProperties();
  }

  private void loadProperties() {
    Properties properties = new Properties();
    try (InputStream input = getClass().getClassLoader().getResourceAsStream(REPORTER_PROPERTIES)) {
      if (input == null) {
        throw new IOException("Could not find the " + REPORTER_PROPERTIES + " file at the classpath");
      }
      properties.load(input);
      openBrowser = Boolean.parseBoolean(properties.getProperty("openBrowser"));
      outputFolder = properties.getProperty("outputFolder");
    } catch (IOException e) {
      throw new ConfigurationException("Could not load the reporter properties.", e);
    }
    LOGGER.debug("Html reporter configuration was loaded successfully.");
  }

  public boolean isOpenBrowser() {
    return openBrowser;
  }

  public void setOpenBrowser(boolean openBrowser) {
    this.openBrowser = openBrowser;
  }

  public String getOutputFolder() {
    return outputFolder;
  }

  public void setOutputFolder(String outputFolder) {
    this.outputFolder = outputFolder;
  }

  @Override
  public String toString() {
    return "HtmlReporterConfiguration{"
        + "openBrowser=" + openBrowser
        + ", outputFolder='" + outputFolder + '\''
        + '}';
  }
}
