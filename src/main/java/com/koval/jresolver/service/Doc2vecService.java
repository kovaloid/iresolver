package com.koval.jresolver.service;

import com.koval.jresolver.core.ModelConstants;
import com.koval.jresolver.core.doc2vec.DocVectorizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class Doc2vecService {

  private static final Logger LOGGER = LoggerFactory.getLogger(Doc2vecService.class);
  private volatile String status = "NONE";

  @Async
  public void train() {
    DocVectorizer docVectorizer = new DocVectorizer();
    LOGGER.info("Start training...");
    status = "TRAIN";
    docVectorizer.createFromResourceWithoutLabels("/raw_sentences.txt");
    LOGGER.info("Start saving...");
    status = "SAVE";
    docVectorizer.save(ModelConstants.DOC2VEC_MODEL_FILE);
    LOGGER.info("Complete!");
    status = "NONE";
  }

  public String status() {
    return status;
  }

  public Collection<String> use(String rawText, int topN) {
    DocVectorizer docVectorizer = new DocVectorizer();
    docVectorizer.load(ModelConstants.DOC2VEC_MODEL_FILE);
    return docVectorizer.getNearestLabels(rawText, topN);
  }
}
