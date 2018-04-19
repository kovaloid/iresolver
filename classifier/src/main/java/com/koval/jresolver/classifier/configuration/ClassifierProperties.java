package com.koval.jresolver.classifier.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ClassifierProperties {

  private int minWordFrequency = 1;
  private int iterations = 5;
  private int epochs = 1;
  private int layerSize = 100;
  private double learningRate = 0.025;
  private int windowSize = 5;
  private boolean trainWordVectors = false;
  private int sampling = 0;

  public ClassifierProperties() {
  }

  public ClassifierProperties(String propertiesFileName) throws IOException {
    Properties properties = new Properties();
    try (InputStream input = ClassifierProperties.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
      if (input == null) {
        throw new IOException("Could not find properties file: " + propertiesFileName);
      }
      properties.load(input);
      minWordFrequency = Integer.parseInt(properties.getProperty("minWordFrequency"));
      iterations = Integer.parseInt(properties.getProperty("iterations"));
      epochs = Integer.parseInt(properties.getProperty("epochs"));
      layerSize = Integer.parseInt(properties.getProperty("layerSize"));
      learningRate = Double.parseDouble(properties.getProperty("learningRate"));
      windowSize = Integer.parseInt(properties.getProperty("windowSize"));
      trainWordVectors = Boolean.parseBoolean(properties.getProperty("trainWordVectors"));
      sampling = Integer.parseInt(properties.getProperty("sampling"));
    }
  }

  public int getMinWordFrequency() {
    return minWordFrequency;
  }

  public void setMinWordFrequency(int minWordFrequency) {
    this.minWordFrequency = minWordFrequency;
  }

  public int getIterations() {
    return iterations;
  }

  public void setIterations(int iterations) {
    this.iterations = iterations;
  }

  public int getEpochs() {
    return epochs;
  }

  public void setEpochs(int epochs) {
    this.epochs = epochs;
  }

  public int getLayerSize() {
    return layerSize;
  }

  public void setLayerSize(int layerSize) {
    this.layerSize = layerSize;
  }

  public double getLearningRate() {
    return learningRate;
  }

  public void setLearningRate(double learningRate) {
    this.learningRate = learningRate;
  }

  public int getWindowSize() {
    return windowSize;
  }

  public void setWindowSize(int windowSize) {
    this.windowSize = windowSize;
  }

  public boolean isTrainWordVectors() {
    return trainWordVectors;
  }

  public void setTrainWordVectors(boolean trainWordVectors) {
    this.trainWordVectors = trainWordVectors;
  }

  public int getSampling() {
    return sampling;
  }

  public void setSampling(int sampling) {
    this.sampling = sampling;
  }
}
