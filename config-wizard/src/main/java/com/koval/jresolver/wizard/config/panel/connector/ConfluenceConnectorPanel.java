package com.koval.jresolver.wizard.config.panel.connector;

import java.awt.*;

import javax.swing.*;


public class ConfluenceConnectorPanel extends JPanel {

  public ConfluenceConnectorPanel() {
    super();
    draw();
  }

  private void draw() {
    setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    JButton updateButton = new JButton("Update");
    add(updateButton);
  }
}
