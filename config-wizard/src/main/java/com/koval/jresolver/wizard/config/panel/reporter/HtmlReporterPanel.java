package com.koval.jresolver.wizard.config.panel.reporter;

import java.util.Properties;

import com.koval.jresolver.wizard.config.ext.OrderedProperties;
import com.koval.jresolver.wizard.config.panel.AbstractWizardPanel;


@SuppressWarnings("PMD")
public class HtmlReporterPanel extends AbstractWizardPanel {

  private static final String FILE_NAME = "html-reporter.properties";

  public HtmlReporterPanel(String configFolder) {
    super("HTML Reporter", configFolder);
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
