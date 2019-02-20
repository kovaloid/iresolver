package com.koval.jresolver.processor.similarity.core.model;

import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.ext.PorterStemmer;

import com.koval.jresolver.processor.similarity.configuration.SimilarityProcessorProperties;


public class StemmingPreprocessor extends CommonPreprocessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(StemmingPreprocessor.class);

  private SimilarityProcessorProperties similarityProcessorProperties;

  public StemmingPreprocessor(SimilarityProcessorProperties similarityProcessorProperties) {
    this.similarityProcessorProperties = similarityProcessorProperties;
  }

  @Override
  public String preProcess(String token) {
    String preparedToken = super.preProcess(token);
    SnowballProgram stemmer = getAppropriateStemmerInstance();
    stemmer.setCurrent(preparedToken);
    stemmer.stem();
    return stemmer.getCurrent();
  }

  private SnowballProgram getAppropriateStemmerInstance() {
    SnowballProgram stemmer = new PorterStemmer();
    try {
      String stemmerClassName = "org.tartarus.snowball.ext." + similarityProcessorProperties.getLanguage() + "Stemmer";
      Class stemmerClass = Class.forName(stemmerClassName);
      stemmer = (SnowballProgram) stemmerClass.newInstance();
    } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
      LOGGER.error("Could not get appropriate class for stemming", e);
    }
    return stemmer;
  }
}
