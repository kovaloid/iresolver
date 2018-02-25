package com.koval.jresolver.core;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareListSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareSentenceIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class DataSetCreator {

  private final static Logger LOGGER = LoggerFactory.getLogger(DataSetCreator.class);

  private WordVectorizer wordVectorizer;

  public DataSetCreator(WordVectorizer wordVectorizer) {
    this.wordVectorizer = wordVectorizer;
  }

  public void create(InputStream input, OutputStream output) {
    try {
      LabelAwareSentenceIterator iterator = new LabelAwareListSentenceIterator(input, "|", 0, 1);
      iterator.setPreProcessor((sentence) -> {
        try {
          String[] labels = iterator.currentLabel().split(",");
          IOUtils.write(labels[0].trim(), output, Charsets.UTF_8);
          for (int i = 1; i < labels.length; i++) {
            IOUtils.write("," + labels[i].trim(), output, Charsets.UTF_8);
          }


          double[] data = wordVectorizer.getVectorFromString(sentence);
          for (double d: data) {
            IOUtils.write("," + Double.toString(d), output, Charsets.UTF_8);
          }
          IOUtils.write("\n", output, Charsets.UTF_8);
        } catch (IOException e) {
          LOGGER.error("Could not process sentence: " + sentence, e);
        }
        return sentence;
      });

      while (iterator.hasNext()) {
        iterator.nextSentence();
      }
    } catch (IOException e) {
      LOGGER.error("Could not create output dataset", e);
    }
  }

  public void create(File fileFrom, File fileTo) {
    try (InputStream input = new FileInputStream(fileFrom);
         OutputStream output = new FileOutputStream(fileTo)) {
      create(input, output);
    } catch (IOException e) {
      LOGGER.error("Could not find files: [" + fileFrom.getAbsolutePath() + ", " + fileTo.getAbsolutePath() + "]", e);
    }
  }

  public void create(String fileFrom, String fileTo) {
    create(new File(fileFrom), new File(fileTo));
  }
}
