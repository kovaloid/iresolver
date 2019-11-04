package com.koval.jresolver.common.api.doc2vec;

import com.koval.jresolver.common.api.exception.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Doc2VecProperties {

  private static final Logger LOGGER = LoggerFactory.getLogger(Doc2VecProperties.class);

  private int minWordFrequency = 1;
  private int iterations = 5;
  private int epochs = 1;
  private int layerSize = 100;
  private double learningRate = 0.025;
  private int windowSize = 5;
  private boolean trainWordVectors;
  private int sampling;
  private String language = "English";
  private String workFolder = "../data/";
  private String vectorModelFileName = "VectorModel.zip";
  private String dataSetFileName = "DataSet.txt";

  public Doc2VecProperties(String propertiesFileName) {
    loadProperties(propertiesFileName);
  }

  private void loadProperties(String propertiesFileName) {
    Properties properties = new Properties();
    try (InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFileName)) {
      if (input == null) {
        throw new IOException("Could not find the " + propertiesFileName + " file at the classpath");
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
      language = properties.getProperty("language");
      workFolder = properties.getProperty("workFolder");
      vectorModelFileName = properties.getProperty("vectorModelFileName");
      dataSetFileName = properties.getProperty("dataSetFileName");
    } catch (IOException e) {
      throw new ConfigurationException("Could not load the doc2vec processor properties.", e);
    }
    LOGGER.debug("Doc2Vec configuration was loaded successfully.");
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

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getWorkFolder() {
    return workFolder;
  }

  public void setWorkFolder(String workFolder) {
    this.workFolder = workFolder;
  }

  public String getVectorModelFileName() {
    return vectorModelFileName;
  }

  public void setVectorModelFileName(String vectorModelFileName) {
    this.vectorModelFileName = vectorModelFileName;
  }

  public String getDataSetFileName() {
    return dataSetFileName;
  }

  public void setDataSetFileName(String dataSetFileName) {
    this.dataSetFileName = dataSetFileName;
  }

  @Override
  public String toString() {
    return "SimilarityProcessorProperties{"
        + "minWordFrequency=" + minWordFrequency
        + ", iterations=" + iterations
        + ", epochs=" + epochs
        + ", layerSize=" + layerSize
        + ", learningRate=" + learningRate
        + ", windowSize=" + windowSize
        + ", trainWordVectors=" + trainWordVectors
        + ", sampling=" + sampling
        + ", language='" + language + '\''
        + ", workFolder='" + workFolder + '\''
        + ", vectorModelFileName='" + vectorModelFileName + '\''
        + ", dataSetFileName='" + dataSetFileName + '\''
        + '}';
  }
}
