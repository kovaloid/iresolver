package com.koval.resolver.core;

import java.awt.*;

import javax.swing.*;


class LauncherUI extends JFrame {

  LauncherUI(final Launcher launcher) {
    super("IResolver Application");
    initLookAndFeel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridLayout(4, 2, 5, 12));

    final JButton createIssuesDataSetBtn = new JButton("Create issues data set");
    final JButton createIssuesVectorModelBtn = new JButton("Create issues vector model");
    final JButton createDocumentationDataSetBtn = new JButton("Create documentation data set");
    final JButton createDocumentationVectorModelBtn = new JButton("Create documentation vector model");
    final JButton createConfluenceDataSetBtn = new JButton("Create confluence data set");
    final JButton createConfluenceVectorModelBtn = new JButton("Create confluence vector model");
    final JButton runBtn = new JButton("Run");
    final JButton printFieldsBtn = new JButton("Print fields");

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
    JFrame.setDefaultLookAndFeelDecorated(true);
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ignored) {
    }
  }
}
