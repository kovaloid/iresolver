package com.koval.jresolver.wizard.config;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import com.koval.jresolver.wizard.config.panel.ControlPanel;
import com.koval.jresolver.wizard.config.panel.connector.BugzillaConnectorPanel;
import com.koval.jresolver.wizard.config.panel.connector.ConfluenceConnectorPanel;
import com.koval.jresolver.wizard.config.panel.connector.JiraConnectorPanel;
import com.koval.jresolver.wizard.config.panel.processor.DocumentationProcessorPanel;
import com.koval.jresolver.wizard.config.panel.processor.IssueSimilarityProcessorPanel;
import com.koval.jresolver.wizard.config.panel.reporter.HtmlReporterPanel;


@SuppressWarnings("PMD")
public class ConfigWizard extends JFrame {

  public static void main(String[] args) {
    new ConfigWizard("./build/resources/main/");
  }

  public ConfigWizard(String configFolder) {
    super("Configuration Wizard");
    initLookAndFeel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridLayout(3, 3, 5, 12));

    add(new JiraConnectorPanel(configFolder));
    add(new BugzillaConnectorPanel(configFolder));
    add(new ConfluenceConnectorPanel(configFolder));
    add(new IssueSimilarityProcessorPanel(configFolder));
    add(new DocumentationProcessorPanel(configFolder));
    add(new ConfluenceConnectorPanel(configFolder));
    add(new HtmlReporterPanel(configFolder));
    add(new ControlPanel(configFolder));
    JButton button = new JButton("Backup all configuration");
    add(button);

    pack();
    setLocationRelativeTo(null);
    setVisible(true);

    addWindowListener(new WindowListener() {
      @Override
      public void windowOpened(WindowEvent e) {
      }

      @Override
      public void windowClosing(WindowEvent e) {
        // new File("control.properties").delete();
        // new File("jira-connector.properties").delete();
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

  private void initLookAndFeel() {
    JFrame.setDefaultLookAndFeelDecorated(true);
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }
  }
}
