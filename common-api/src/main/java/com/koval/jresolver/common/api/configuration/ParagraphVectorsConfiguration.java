package com.koval.jresolver.common.api.configuration;

public class ParagraphVectorsConfiguration {

  private int minWordFrequency;
  private int iterations;
  private int epochs;
  private int layerSize;
  private double learningRate;
  private int windowSize;
  private boolean trainWordVectors;
  private int sampling;
  private String language;

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

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }
}
