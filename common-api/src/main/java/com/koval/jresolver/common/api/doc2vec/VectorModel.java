package com.koval.jresolver.common.api.doc2vec;

import java.util.Collection;

import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;


public class VectorModel {

  private final ParagraphVectors paragraphVectors;

  public VectorModel(ParagraphVectors paragraphVectors) {
    this.paragraphVectors = paragraphVectors;
  }

  public ParagraphVectors getParagraphVectors() {
    return paragraphVectors;
  }

  public double getSimilarity(String label1, String label2) {
    return paragraphVectors.similarity(label1, label2);
  }

  public Collection<String> getNearestLabels(String rawText, int topN) {
    return paragraphVectors.nearestLabels(rawText, topN);
  }
}
