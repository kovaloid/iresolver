package com.koval.jresolver.wizard.config.panel.reporter;

import java.awt.*;

import javax.swing.*;


public class HtmlReporterPanel extends JPanel {

  public HtmlReporterPanel() {
    super();
    draw();
  }

  private void draw() {
    setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    JButton updateButton = new JButton("Update");
    add(updateButton);
  }
}
