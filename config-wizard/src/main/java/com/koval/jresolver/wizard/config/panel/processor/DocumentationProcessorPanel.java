package com.koval.jresolver.wizard.config.panel.processor;

import java.awt.*;

import javax.swing.*;


public class DocumentationProcessorPanel extends JPanel {

  public DocumentationProcessorPanel() {
    super();
    draw();
  }

  private void draw() {
    setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    JButton updateButton = new JButton("Update");
    add(updateButton);
  }
}
