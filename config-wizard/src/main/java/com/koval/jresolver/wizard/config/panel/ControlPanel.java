package com.koval.jresolver.wizard.config.panel;

import java.awt.*;

import javax.swing.*;


public class ControlPanel extends JPanel {

  public ControlPanel() {
    super();
    draw();
  }

  private void draw() {
    setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    JButton updateButton = new JButton("Update");
    add(updateButton);
  }
}
