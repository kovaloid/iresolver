package com.koval.jresolver.classifier.core.impl;

import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.tartarus.snowball.ext.PorterStemmer;


public class StemmingPreprocessor extends CommonPreprocessor {

  @Override
  public String preProcess(String token) {
    String prep = super.preProcess(token);
    PorterStemmer stemmer = new PorterStemmer();
    stemmer.setCurrent(prep);
    stemmer.stem();
    return stemmer.getCurrent();
  }
}
