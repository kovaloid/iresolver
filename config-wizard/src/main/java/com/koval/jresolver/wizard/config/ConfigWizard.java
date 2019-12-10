package com.koval.jresolver.wizard.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class ConfigWizard extends Application {

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    String javaVersion = System.getProperty("java.version");
    String javafxVersion = System.getProperty("javafx.version");

    Label info = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

    ComboBox<String> connector = new ComboBox<>();
    connector.getItems().addAll("jira", "bugzilla");

    Button generateButton = new Button("Generate");
    generateButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
      createConfigurationFilesSet(connector.getValue());
    });

    Scene scene = new Scene(new FlowPane(info, connector, generateButton), 640, 480);

    primaryStage.setTitle("Configuration Wizard");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  @Override
  public void stop() throws Exception {
    new File("control.properties").delete();
    new File("jira-connector.properties").delete();
    // bugzilla-connector
    // confluence-connector
    // confluence-processor
    // control
    // documentation-processor
    // html-reporter
    // jira-connector
    // similarity-processor
  }

  private void createConfigurationFilesSet(String option) {
    Properties control = new Properties();
    control.setProperty("connector", option);
    control.setProperty("processors", "value2");
    control.setProperty("reporters", "value2");
    control.setProperty("parallel", "value2");
    createConfigurationFile("control.properties", "Control properties file", control);

    Properties jira = new Properties();
    jira.setProperty("argument3", option);
    jira.setProperty("argument4", "value4");
    createConfigurationFile("jira-connector.properties", "Jira properties file", jira);
  }

  private void createConfigurationFile(String fileName, String description, Properties properties) {
    try (OutputStream output = new FileOutputStream(fileName)) {
      properties.store(output, description);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
