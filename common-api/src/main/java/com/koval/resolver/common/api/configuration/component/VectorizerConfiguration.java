package com.koval.resolver.common.api.configuration.component;


public class VectorizerConfiguration {

  private int batchSize;
  private int iterations;
  private int epochs;
  private int layerSize;
  private double learningRate;
  private int minWordFrequency;
  private int sampling;
  private int windowSize;
  private int topResults;
  private String language;

  public int getBatchSize() {
    return batchSize;
  }

  public void setBatchSize(final int batchSize) {
    this.batchSize = batchSize;
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

  public int getMinWordFrequency() {
    return minWordFrequency;
  }

  public void setMinWordFrequency(final int minWordFrequency) {
    this.minWordFrequency = minWordFrequency;
  }

  public int getSampling() {
    return sampling;
  }

  public void setSampling(final int sampling) {
    this.sampling = sampling;
  }

  public int getWindowSize() {
    return windowSize;
  }

  public void setWindowSize(final int windowSize) {
    this.windowSize = windowSize;
  }

  public int getTopResults() {
    return topResults;
  }

  public void setTopResults(final int topResults) {
    this.topResults = topResults;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(final String language) {
    this.language = language;
  }

  @Override
  public String toString() {
    return "VectorizerConfiguration{"
        + "batchSize=" + batchSize + '\''
        + ", iterations=" + iterations + '\''
        + ", epochs=" + epochs + '\''
        + ", layerSize=" + layerSize + '\''
        + ", learningRate=" + learningRate + '\''
        + ", minWordFrequency=" + minWordFrequency + '\''
        + ", sampling=" + sampling + '\''
        + ", windowSize=" + windowSize + '\''
        + ", topResults=" + topResults + '\''
        + ", language='" + language + '\''
        + '}';
  }
}
