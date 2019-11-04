package com.koval.jresolver.common.api.doc2vec;

import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.ext.PorterStemmer;


public class StemmingPreprocessor extends CommonPreprocessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(StemmingPreprocessor.class);

  private String language = "English";

  public StemmingPreprocessor setLanguage(String lang) {
    this.language = lang;
    return this;
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
      String stemmerClassName = "org.tartarus.snowball.ext." + language + "Stemmer";
      Class stemmerClass = Class.forName(stemmerClassName);
      stemmer = (SnowballProgram)stemmerClass.newInstance();
    } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
      LOGGER.error("Could not get appropriate class for stemming. The default one will be used.", e);
    }
    return stemmer;
  }
}
