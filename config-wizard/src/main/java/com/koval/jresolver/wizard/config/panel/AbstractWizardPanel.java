package com.koval.jresolver.wizard.config.panel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Objects;
import java.util.Properties;

import javax.swing.*;

import com.koval.jresolver.wizard.config.ext.OrderedProperties;


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
    Properties properties = new OrderedProperties();
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

  public void setTextFieldAsNumeric(JTextField component) {
    component.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ke) {
        String value = component.getText();
        int l = value.length();
        if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
          component.setEditable(true);
        } else {
          component.setEditable(false);
          JOptionPane.showMessageDialog(null, "* Enter only numeric digits(0-9)");
        }
      }
    });
  }
}
