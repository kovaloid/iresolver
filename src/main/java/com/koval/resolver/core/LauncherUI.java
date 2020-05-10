package com.koval.resolver.core;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;


class LauncherUI extends JFrame {
  
  LauncherUI(Launcher launcher) {
    super("IResolver Application");
    initLookAndFeel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridLayout(4, 2, 5, 12));

    JButton createIssuesDataSetBtn = new JButton("Create issues data set");
    JButton createIssuesVectorModelBtn = new JButton("Create issues vector model");
    JButton createDocumentationDataSetBtn = new JButton("Create documentation data set");
    JButton createDocumentationVectorModelBtn = new JButton("Create documentation vector model");
    JButton createConfluenceDataSetBtn = new JButton("Create confluence data set");
    JButton createConfluenceVectorModelBtn = new JButton("Create confluence vector model");
    JButton runBtn = new JButton("Run");
    JButton printFieldsBtn = new JButton("Print fields");

    createIssuesDataSetBtn.addActionListener(e -> launcher.createIssuesDataSet());
    createIssuesVectorModelBtn.addActionListener(e -> launcher.createIssuesVectorModel());
    createDocumentationDataSetBtn.addActionListener(e -> launcher.createDocumentationDataSet());
    createDocumentationVectorModelBtn.addActionListener(e -> launcher.createDocumentationVectorModel());
    createConfluenceDataSetBtn.addActionListener(e -> launcher.createConfluenceDataSet());
    createConfluenceVectorModelBtn.addActionListener(e -> launcher.createConfluenceVectorModel());
    runBtn.addActionListener(e -> launcher.run());
    printFieldsBtn.addActionListener(e -> launcher.printIssueFields());

    add(createIssuesDataSetBtn);
    add(createIssuesVectorModelBtn);
    add(createDocumentationDataSetBtn);
    add(createDocumentationVectorModelBtn);
    add(createConfluenceDataSetBtn);
    add(createConfluenceVectorModelBtn);
    add(runBtn);
    add(printFieldsBtn);

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void initLookAndFeel() {
    final Logger logger = Logger.getLogger(LauncherUI.class.getName());
    JFrame.setDefaultLookAndFeelDecorated(true);
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
      logger.severe(e.getMessage());
    }
  }
}
