package com.koval.jresolver.wizard.config;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.*;


@SuppressWarnings("PMD")
public final class ConfigWizard {

  private ConfigWizard() {
  }

  public static void main(String[] args) {
    JFrame.setDefaultLookAndFeelDecorated(true);

    JFrame frame = new JFrame("Configuration Wizard");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.getContentPane().add(new JiraConnectorPanel());

    String javaVersion = System.getProperty("java.version");
    JLabel info = new JLabel("Hello, running on Java " + javaVersion + ".");

    JComboBox<String> options = new JComboBox<>();
    options.addItem("jira");
    options.addItem("bugzilla");

    JButton button = new JButton("Generate");

    button.addActionListener((actionEvent) -> {
      System.out.println(actionEvent);
      createConfigurationFilesSet((String)options.getSelectedItem());
    });

    frame.getContentPane().setLayout(new FlowLayout());
    frame.getContentPane().add(info);
    frame.getContentPane().add(button);

    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);


    frame.addWindowListener(new WindowListener() {
      @Override
      public void windowOpened(WindowEvent e) {
      }

      @Override
      public void windowClosing(WindowEvent e) {
        new File("control.properties").delete();
        new File("jira-connector.properties").delete();
      }

      @Override
      public void windowClosed(WindowEvent e) {
      }

      @Override
      public void windowIconified(WindowEvent e) {
      }

      @Override
      public void windowDeiconified(WindowEvent e) {
      }

      @Override
      public void windowActivated(WindowEvent e) {
      }

      @Override
      public void windowDeactivated(WindowEvent e) {
      }
    });

  }

  private static void createConfigurationFilesSet(String option) {
    Properties control = new Properties();
    control.setProperty("connector", option);
    control.setProperty("processors", "value2");
    control.setProperty("reporters", "value2");
    control.setProperty("parallel", "value2");
    createConfigurationFile("control.properties", "Control properties file", control);

    Properties jira = new Properties();
    jira.setProperty("argument3", option);
    jira.setProperty("argument4", "value4");
    createConfigurationFile("jira-connector.properties", "Jira properties file", jira);
  }

  private static void createConfigurationFile(String fileName, String description, Properties properties) {
    try (OutputStream output = new FileOutputStream(fileName)) {
      properties.store(output, description);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
