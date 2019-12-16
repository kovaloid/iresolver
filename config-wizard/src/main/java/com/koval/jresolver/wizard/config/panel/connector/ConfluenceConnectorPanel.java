package com.koval.jresolver.wizard.config.panel.connector;

import java.util.Properties;

import com.koval.jresolver.wizard.config.panel.AbstractWizardPanel;


@SuppressWarnings("PMD")
public class ConfluenceConnectorPanel extends AbstractWizardPanel {

  private static final String FILE_NAME = "confluence-connector.properties";

  public ConfluenceConnectorPanel(String configFolder) {
    super("Confluence Connector", configFolder);
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
