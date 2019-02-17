package com.koval.jresolver.processor.similarity.core;

import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.ext.PorterStemmer;

import com.koval.jresolver.processor.similarity.configuration.Doc2VecProperties;


public class StemmingPreprocessor extends CommonPreprocessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(StemmingPreprocessor.class);

  @Override
  public String preProcess(String token) {
    String prep = super.preProcess(token);
    SnowballProgram stemmer = new PorterStemmer();
    try {
      Doc2VecProperties doc2vecProperties = new Doc2VecProperties();
      Class stemmerClass = Class.forName("org.tartarus.snowball.ext." + doc2vecProperties.getLanguage() + "Stemmer");
      stemmer = (SnowballProgram)stemmerClass.newInstance();
    } catch (Exception e) {
      LOGGER.error("Could not find a stemmer", e);
    }
    stemmer.setCurrent(prep);
    stemmer.stem();
    return stemmer.getCurrent();
  }
}
