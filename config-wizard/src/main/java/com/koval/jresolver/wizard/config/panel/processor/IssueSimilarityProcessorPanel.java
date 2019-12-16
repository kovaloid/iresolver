package com.koval.jresolver.wizard.config.panel.processor;

import java.util.Properties;

import com.koval.jresolver.wizard.config.panel.AbstractWizardPanel;


@SuppressWarnings("PMD")
public class IssueSimilarityProcessorPanel extends AbstractWizardPanel {

  private static final String FILE_NAME = "similarity-processor.properties";

  public IssueSimilarityProcessorPanel(String configFolder) {
    super("Similarity Processor", configFolder);
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
