package com.koval.jresolver.wizard.config.panel.connector;

import java.awt.*;
import java.util.Properties;

import javax.swing.*;

import com.koval.jresolver.wizard.config.ext.ConfigWizardPopupMenu;
import com.koval.jresolver.wizard.config.ext.OrderedProperties;
import com.koval.jresolver.wizard.config.panel.AbstractWizardPanel;


@SuppressWarnings("PMD")
public class JiraConnectorPanel extends AbstractWizardPanel {

  private static final String FILE_NAME = "jira-connector.properties";
  private JTextField url;
  private JTextField browseUrl;
  private JComboBox<String> anonymous;
  private JTextField resolvedQuery;
  private JTextField unresolvedQuery;
  private JTextField batchSize;
  private JTextField batchDelay;
  private JTextField resolvedIssueFields;
  private JTextField unresolvedIssueFields;
  private JTextField credentialsFolder;

  public JiraConnectorPanel(String configFolder) {
    super("Jira Connector", configFolder);
    draw();
    addDefaultButtons();
    initFields();
  }

  @Override
  public void draw() {
    JPanel panel = new JPanel(new GridLayout(10, 2, 5, 2));

    JLabel urlLabel = new JLabel("URL");
    JLabel browseUrlLabel = new JLabel("Browse URL");
    JLabel anonymousLabel = new JLabel("Anonymous");
    JLabel resolvedQueryLabel = new JLabel("Resolved Query");
    JLabel unresolvedQueryLabel = new JLabel("Unresolved Query");
    JLabel batchSizeLabel = new JLabel("Batch Size");
    JLabel batchDelayLabel = new JLabel("Batch Delay");
    JLabel resolvedIssueFieldsLabel = new JLabel("Resolved Issue Fields");
    JLabel unresolvedIssueFieldsLabel = new JLabel("Unresolved Issue Fields");
    JLabel credentialsFolderLabel = new JLabel("Credentials Folder");

    url = new JTextField(20);
    browseUrl = new JTextField(20);
    anonymous = new JComboBox<>(new String[] {
        Boolean.TRUE.toString().toLowerCase(),
        Boolean.FALSE.toString().toLowerCase()
    });
    resolvedQuery = new JTextField(20);
    unresolvedQuery = new JTextField(20);
    batchSize = new JTextField(20);
    batchDelay = new JTextField(20);
    resolvedIssueFields = new JTextField(20);
    unresolvedIssueFields = new JTextField(20);
    credentialsFolder = new JTextField(20);

    url.setComponentPopupMenu(new ConfigWizardPopupMenu());
    browseUrl.setComponentPopupMenu(new ConfigWizardPopupMenu());
    anonymous.setComponentPopupMenu(new ConfigWizardPopupMenu());
    resolvedQuery.setComponentPopupMenu(new ConfigWizardPopupMenu());
    unresolvedQuery.setComponentPopupMenu(new ConfigWizardPopupMenu());
    batchSize.setComponentPopupMenu(new ConfigWizardPopupMenu());
    batchDelay.setComponentPopupMenu(new ConfigWizardPopupMenu());
    resolvedIssueFields.setComponentPopupMenu(new ConfigWizardPopupMenu());
    unresolvedIssueFields.setComponentPopupMenu(new ConfigWizardPopupMenu());
    credentialsFolder.setComponentPopupMenu(new ConfigWizardPopupMenu());

    setTextFieldAsNumeric(batchSize);
    setTextFieldAsNumeric(batchDelay);

    panel.add(urlLabel);
    panel.add(url);
    panel.add(browseUrlLabel);
    panel.add(browseUrl);
    panel.add(anonymousLabel);
    panel.add(anonymous);
    panel.add(resolvedQueryLabel);
    panel.add(resolvedQuery);
    panel.add(unresolvedQueryLabel);
    panel.add(unresolvedQuery);
    panel.add(batchSizeLabel);
    panel.add(batchSize);
    panel.add(batchDelayLabel);
    panel.add(batchDelay);
    panel.add(resolvedIssueFieldsLabel);
    panel.add(resolvedIssueFields);
    panel.add(unresolvedIssueFieldsLabel);
    panel.add(unresolvedIssueFields);
    panel.add(credentialsFolderLabel);
    panel.add(credentialsFolder);

    add(panel);
  }

  @Override
  public void initFields() {
    Properties properties = getProperties(FILE_NAME);
    url.setText(properties.getProperty("url"));
    browseUrl.setText(properties.getProperty("browseUrl"));
    anonymous.setSelectedItem(properties.getProperty("anonymous"));
    resolvedQuery.setText(properties.getProperty("resolvedQuery"));
    unresolvedQuery.setText(properties.getProperty("unresolvedQuery"));
    batchSize.setText(properties.getProperty("batchSize"));
    batchDelay.setText(properties.getProperty("batchDelay"));
    resolvedIssueFields.setText(properties.getProperty("resolvedIssueFields"));
    unresolvedIssueFields.setText(properties.getProperty("unresolvedIssueFields"));
    credentialsFolder.setText(properties.getProperty("credentialsFolder"));
  }

  @Override
  public void saveFields() {
    Properties properties = new OrderedProperties();
    properties.setProperty("url", url.getText());
    properties.setProperty("browseUrl", browseUrl.getText());
    properties.setProperty("anonymous", (String)anonymous.getSelectedItem());
    properties.setProperty("resolvedQuery", resolvedQuery.getText());
    properties.setProperty("unresolvedQuery", unresolvedQuery.getText());
    properties.setProperty("batchSize", batchSize.getText());
    properties.setProperty("batchDelay", batchDelay.getText());
    properties.setProperty("resolvedIssueFields", resolvedIssueFields.getText());
    properties.setProperty("unresolvedIssueFields", unresolvedIssueFields.getText());
    properties.setProperty("credentialsFolder", credentialsFolder.getText());
    saveProperties(FILE_NAME, properties);
  }
}
