package com.koval.jresolver.wizard.config.panel.processor;

import java.util.Properties;

import com.koval.jresolver.wizard.config.ext.OrderedProperties;
import com.koval.jresolver.wizard.config.panel.AbstractWizardPanel;


@SuppressWarnings("PMD")
public class DocumentationProcessorPanel extends AbstractWizardPanel {

  private static final String FILE_NAME = "documentation-processor.properties";

  public DocumentationProcessorPanel(String configFolder) {
    super("Documentation Processor", configFolder);
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
    Properties properties = new OrderedProperties();
    saveProperties(FILE_NAME, properties);
  }
}
