package com.koval.jresolver.processor.similarity.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimilarityProcessorProperties {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimilarityProcessorProperties.class);

  private static final String SIMILARITY_PROCESSOR_PROPERTIES_FILE = "similarity-processor.properties";

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

  public SimilarityProcessorProperties() {
    loadProperties();
  }

  private void loadProperties() {
    Properties properties = new Properties();
    try (InputStream input = getClass().getClassLoader().getResourceAsStream(SIMILARITY_PROCESSOR_PROPERTIES_FILE)) {
      if (input == null) {
        throw new IOException("Could not find the " + SIMILARITY_PROCESSOR_PROPERTIES_FILE + " file at the classpath");
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
      LOGGER.error("Could not load the similarity processor properties. The default properties will be used.", e);
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
}
