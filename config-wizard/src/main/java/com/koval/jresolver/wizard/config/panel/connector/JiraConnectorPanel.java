package com.koval.jresolver.wizard.config.panel.connector;

import java.util.Properties;

import javax.swing.*;

import com.koval.jresolver.wizard.config.panel.AbstractWizardPanel;


@SuppressWarnings("PMD")
public class JiraConnectorPanel extends AbstractWizardPanel {

  private static final String FILE_NAME = "jira-connector.properties";

  public JiraConnectorPanel(String configFolder) {
    super("Jira Connector", configFolder);
    draw();
    addDefaultButtons();
    initFields();
  }

  @Override
  public void draw() {
    JLabel urlLabel = new JLabel("URL");
    JTextField urlTextField = new JTextField(10);
    add(urlLabel);
    add(urlTextField);
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
