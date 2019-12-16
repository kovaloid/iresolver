package com.koval.jresolver.wizard.config.panel;

import java.awt.*;
import java.io.*;
import java.util.Objects;
import java.util.Properties;

import javax.swing.*;


@SuppressWarnings("PMD")
public abstract class AbstractWizardPanel extends JPanel {

  private final String configFolder;

  protected AbstractWizardPanel(String title, String configFolder) {
    this.configFolder = configFolder;
    setToolTipText(title);
    setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), title));
  }

  protected void addDefaultButtons() {
    JButton updateButton = new JButton("Update");
    add(updateButton);
    updateButton.addActionListener((actionEvent) -> saveFields());

    JButton resetButton = new JButton("Reset");
    add(resetButton);
    resetButton.addActionListener((actionEvent) -> initFields());
  }

  protected abstract void draw();

  protected abstract void initFields();

  protected abstract void saveFields();

  protected Properties getProperties(String configFile) {
    Properties properties = new Properties();
    try (InputStream input = new FileInputStream(new File(configFolder, configFile))) {
      properties.load(Objects.requireNonNull(input));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return properties;
  }

  protected void saveProperties(String configFile, Properties properties) {
    try (OutputStream output = new FileOutputStream(new File(configFolder, configFile))) {
      properties.store(output, "JResolver configuration file");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
