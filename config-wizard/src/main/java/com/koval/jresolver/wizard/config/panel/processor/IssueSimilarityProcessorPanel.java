package com.koval.jresolver.wizard.config.panel.processor;

import java.awt.*;

import javax.swing.*;


public class IssueSimilarityProcessorPanel extends JPanel {

  public IssueSimilarityProcessorPanel() {
    super();
    draw();
  }

  private void draw() {
    setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    JButton updateButton = new JButton("Update");
    add(updateButton);
  }
}
