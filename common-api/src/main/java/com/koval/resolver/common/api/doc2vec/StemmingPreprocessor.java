package com.koval.resolver.common.api.doc2vec;

import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.ext.DanishStemmer;
import org.tartarus.snowball.ext.DutchStemmer;
import org.tartarus.snowball.ext.EnglishStemmer;
import org.tartarus.snowball.ext.FinnishStemmer;
import org.tartarus.snowball.ext.FrenchStemmer;
import org.tartarus.snowball.ext.GermanStemmer;
import org.tartarus.snowball.ext.HungarianStemmer;
import org.tartarus.snowball.ext.ItalianStemmer;
import org.tartarus.snowball.ext.NorwegianStemmer;
import org.tartarus.snowball.ext.PorterStemmer;
import org.tartarus.snowball.ext.PortugueseStemmer;
import org.tartarus.snowball.ext.RomanianStemmer;
import org.tartarus.snowball.ext.RussianStemmer;
import org.tartarus.snowball.ext.SpanishStemmer;
import org.tartarus.snowball.ext.SwedishStemmer;
import org.tartarus.snowball.ext.TurkishStemmer;


public class StemmingPreprocessor extends CommonPreprocessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(StemmingPreprocessor.class);

  private String language = "English";

  public StemmingPreprocessor setLanguage(final String lang) {
    this.language = lang;
    return this;
  }

  @Override
  public String preProcess(final String token) {
    LOGGER.debug("Stemming of token: {}", token);
    final String preparedToken = super.preProcess(token);
    final SnowballProgram stemmer = getAppropriateStemmerInstance();
    stemmer.setCurrent(preparedToken);
    stemmer.stem();
    return stemmer.getCurrent();
  }

  @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.StdCyclomaticComplexity"})
  private SnowballProgram getAppropriateStemmerInstance() {
    switch (language) {
      case "Danish":
        return new DanishStemmer();
      case "Dutch":
        return new DutchStemmer();
      case "English":
        return new EnglishStemmer();
      case "Finnish":
        return new FinnishStemmer();
      case "French":
        return new FrenchStemmer();
      case "German":
        return new GermanStemmer();
      case "Hungarian":
        return new HungarianStemmer();
      case "Italian":
        return new ItalianStemmer();
      case "Norwegian":
        return new NorwegianStemmer();
      case "Portuguese":
        return new PortugueseStemmer();
      case "Romanian":
        return new RomanianStemmer();
      case "Russian":
        return new RussianStemmer();
      case "Spanish":
        return new SpanishStemmer();
      case "Swedish":
        return new SwedishStemmer();
      case "Turkish":
        return new TurkishStemmer();
      default:
        return new PorterStemmer();
    }
  }
}
