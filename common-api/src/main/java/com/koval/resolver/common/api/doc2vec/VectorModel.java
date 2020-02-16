package com.koval.resolver.common.api.doc2vec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;


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

  public double similarityToLabel(String rawText, String label) {
    if (paragraphVectors.getTokenizerFactory() == null) {
      throw new IllegalStateException("TokenizerFactory should be defined, prior to predict() call");
    }
    VocabCache<VocabWord> vocabCache = paragraphVectors.getVocab();
    List<String> tokens = paragraphVectors.getTokenizerFactory()
        .create(rawText)
        .getTokens();
    List<VocabWord> document = new ArrayList<>();
    for (String token : tokens) {
      if (vocabCache.containsWord(token)) {
        document.add(vocabCache.wordFor(token));
      }
    }
    return paragraphVectors.similarityToLabel(document, label);
  }
}
