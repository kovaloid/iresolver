package com.koval.resolver.common.api.vectorization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;


public class VectorModel {

  private final ParagraphVectors vectorizer;
  private final int topResults;

  public VectorModel(final ParagraphVectors vectorizer, final int topResults) {
    this.vectorizer = vectorizer;
    this.topResults = topResults;
  }

  ParagraphVectors getVectorizer() {
    return vectorizer;
  }

  public double getSimilarity(final String label1, final String label2) {
    return vectorizer.similarity(label1, label2);
  }

  public Collection<String> getNearestLabels(final String rawText) {
    return vectorizer.nearestLabels(rawText, topResults);
  }

  public double similarityToLabel(final String rawText, final String label) {
    if (vectorizer.getTokenizerFactory() == null) {
      throw new IllegalStateException("TokenizerFactory should be defined, prior to predict() call");
    }
    final VocabCache<VocabWord> vocabCache = vectorizer.getVocab();
    final List<String> tokens = vectorizer.getTokenizerFactory()
        .create(rawText)
        .getTokens();
    final List<VocabWord> document = new ArrayList<>();
    for (final String token : tokens) {
      if (vocabCache.containsWord(token)) {
        document.add(vocabCache.wordFor(token));
      }
    }
    return vectorizer.similarityToLabel(document, label);
  }
}
