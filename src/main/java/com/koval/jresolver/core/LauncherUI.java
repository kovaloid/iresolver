package com.koval.jresolver.core;

import java.awt.*;

import javax.swing.*;


class LauncherUI extends JFrame {

  LauncherUI(Launcher launcher) {
    super("JResolver Application");
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

    createIssuesDataSetBtn.addActionListener(e -> launcher.createSimilarityDataSet());
    createIssuesVectorModelBtn.addActionListener(e -> launcher.createSimilarityVectorModel());
    createDocumentationDataSetBtn.addActionListener(e -> launcher.createDocumentationDataSet());
    createDocumentationVectorModelBtn.addActionListener(e -> launcher.createDocumentationVectorModel());
    createConfluenceDataSetBtn.addActionListener(e -> launcher.createConfluenceDataSet());
    createConfluenceVectorModelBtn.addActionListener(e -> launcher.createConfluenceVectorModel());
    runBtn.addActionListener(e -> launcher.run());
    printFieldsBtn.addActionListener(e -> launcher.printFields());

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
    JFrame.setDefaultLookAndFeelDecorated(true);
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ignored) {
    }
  }
}
