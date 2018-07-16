package com.koval.jresolver.classifier.core.impl;

import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.ext.*;

import com.koval.jresolver.classifier.configuration.ClassifierProperties;

public class StemmingPreprocessor extends CommonPreprocessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(StemmingPreprocessor.class);

  @Override
  public String preProcess(String token) {
    String prep = super.preProcess(token);
    SnowballProgram stemmer = new PorterStemmer();
    try {
      ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
      Class stemmerClass = Class.forName("org.tartarus.snowball.ext."
              + classifierProperties.getLanguage() + "Stemmer");
      stemmer = (SnowballProgram)stemmerClass.newInstance();
    } catch (Exception e) {
      LOGGER.error("Could not find a stemmer", e);
    }
    stemmer.setCurrent(prep);
    stemmer.stem();
    return stemmer.getCurrent();
  }
}
