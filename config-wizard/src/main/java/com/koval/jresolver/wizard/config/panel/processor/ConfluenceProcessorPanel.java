package com.koval.jresolver.wizard.config.panel.processor;

import java.util.Properties;

import com.koval.jresolver.wizard.config.panel.AbstractWizardPanel;


@SuppressWarnings("PMD")
public class ConfluenceProcessorPanel extends AbstractWizardPanel {

  private static final String FILE_NAME = "confluence-processor.properties";

  public ConfluenceProcessorPanel(String configFolder) {
    super("Confluence Processor", configFolder);
    draw();
    addDefaultButtons();
    initFields();
  }

  @Override
  public void draw() {
  }

  @Override
  public void initFields() {
    Properties properties = getProperties(FILE_NAME);
  }

  @Override
  public void saveFields() {
    Properties properties = new Properties();
    saveProperties(FILE_NAME, properties);
  }
}
