package com.koval.resolver.common.api.configuration.bean;


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

  public void setMinWordFrequency(final int minWordFrequency) {
    this.minWordFrequency = minWordFrequency;
  }

  public int getIterations() {
    return iterations;
  }

  public void setIterations(final int iterations) {
    this.iterations = iterations;
  }

  public int getEpochs() {
    return epochs;
  }

  public void setEpochs(final int epochs) {
    this.epochs = epochs;
  }

  public int getLayerSize() {
    return layerSize;
  }

  public void setLayerSize(final int layerSize) {
    this.layerSize = layerSize;
  }

  public double getLearningRate() {
    return learningRate;
  }

  public void setLearningRate(final double learningRate) {
    this.learningRate = learningRate;
  }

  public int getWindowSize() {
    return windowSize;
  }

  public void setWindowSize(final int windowSize) {
    this.windowSize = windowSize;
  }

  public boolean isTrainWordVectors() {
    return trainWordVectors;
  }

  public void setTrainWordVectors(final boolean trainWordVectors) {
    this.trainWordVectors = trainWordVectors;
  }

  public int getSampling() {
    return sampling;
  }

  public void setSampling(final int sampling) {
    this.sampling = sampling;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(final String language) {
    this.language = language;
  }

  @Override
  public String toString() {
    return "ParagraphVectorsConfiguration{"
        + "minWordFrequency=" + minWordFrequency
        + ", iterations=" + iterations
        + ", epochs=" + epochs
        + ", layerSize=" + layerSize
        + ", learningRate=" + learningRate
        + ", windowSize=" + windowSize
        + ", trainWordVectors=" + trainWordVectors
        + ", sampling=" + sampling
        + ", language='" + language + '\''
        + '}';
  }
}
