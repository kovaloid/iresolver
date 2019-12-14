package com.koval.jresolver.wizard.config.panel.connector;

import java.awt.*;

import javax.swing.*;


public class JiraConnectorPanel extends JPanel {

  public static final String TITLE = "Jira Connector";

  public JiraConnectorPanel() {
    super();
    draw();
  }

  private void draw() {
    setToolTipText(TITLE);
    setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), TITLE));

    JLabel urlLabel = new JLabel("URL");
    JTextField urlTextField = new JTextField(10);
    add(urlLabel);
    add(urlTextField);

    JButton updateButton = new JButton("Update");
    add(updateButton);
  }

}
