package com.koval.jresolver.wizard.config.panel;

import java.util.Properties;

import javax.swing.*;


@SuppressWarnings("PMD")
public class ControlPanel extends AbstractWizardPanel {

  private static final String FILE_NAME = "control.properties";
  private final JComboBox<String> connector = new JComboBox<>();

  public ControlPanel(String configFolder) {
    super("Control", configFolder);
    draw();
    addDefaultButtons();
    initFields();
  }

  @Override
  public void draw() {
    connector.addItem("jira");
    connector.addItem("bugzilla");
    add(connector);
  }

  @Override
  public void initFields() {
    Properties properties = getProperties(FILE_NAME);
    connector.setSelectedItem(properties.getProperty("connector"));
  }

  @Override
  public void saveFields() {
    Properties properties = new Properties();
    properties.setProperty("connector", (String)connector.getSelectedItem());
    properties.setProperty("processors", "value2");
    properties.setProperty("reporters", "value2");
    properties.setProperty("parallel", "value2");
    saveProperties(FILE_NAME, properties);
  }
}
