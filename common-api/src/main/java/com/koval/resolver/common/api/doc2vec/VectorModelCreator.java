package com.koval.resolver.common.api.doc2vec;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.deeplearning4j.models.embeddings.learning.impl.sequence.DBOW;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.AbstractCache;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareListSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareSentenceIterator;
import org.deeplearning4j.text.stopwords.StopWords;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.configuration.bean.ParagraphVectorsConfiguration;


public class VectorModelCreator {

  private static final Logger LOGGER = LoggerFactory.getLogger(VectorModelCreator.class);

  private TokenPreProcess tokenPreprocessor;
  private List<String> stopWords;
  private ParagraphVectorsConfiguration properties;

  public VectorModelCreator(final ParagraphVectorsConfiguration properties) {
    this(new StemmingPreprocessor().setLanguage(properties.getLanguage()), StopWords.getStopWords(),
        properties);
  }

  public VectorModelCreator(final TokenPreProcess tokenPreprocessor, final List<String> stopWords, final ParagraphVectorsConfiguration properties) {
    this.tokenPreprocessor = tokenPreprocessor;
    this.stopWords = stopWords;
    this.properties = properties;
  }

  public VectorModel createFromFile(final File inputFile) throws IOException {
    try (InputStream inputStream = new FileInputStream(inputFile)) {
      return createFromInputStream(inputStream);
    }
  }

  public VectorModel createFromResource(final String dataSetFileName) throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(dataSetFileName)) {
      return createFromInputStream(inputStream);
    }
  }

  public VectorModel createFromInputStream(final InputStream inputStream) throws IOException {
    final LabelAwareSentenceIterator iterator = new LabelAwareListSentenceIterator(inputStream, "|", 0, 1);
    return createVectorModelWithIterator(iterator);
  }

  private VectorModel createVectorModelWithIterator(final LabelAwareSentenceIterator iterator) {
    final AbstractCache<VocabWord> cache = new AbstractCache<>();

    final TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
    tokenizerFactory.setTokenPreProcessor(tokenPreprocessor);

    final ParagraphVectors paragraphVectors = new ParagraphVectors.Builder()
        .sequenceLearningAlgorithm(new DBOW<>())
        // .setVectorsListeners(Collections.singletonList(
        //     new SimilarityListener<>(ListenerEvent.EPOCH, 1, "AMQ-6134 ", "AMQ-5100 "))
        // )
        .minWordFrequency(properties.getMinWordFrequency())
        .iterations(properties.getIterations())
        .epochs(properties.getEpochs())
        .layerSize(properties.getLayerSize())
        .learningRate(properties.getLearningRate())
        .windowSize(properties.getWindowSize())
        .trainWordVectors(properties.isTrainWordVectors())
        .sampling(properties.getSampling())
        .iterate(iterator)
        .vocabCache(cache)
        .tokenizerFactory(tokenizerFactory)
        .stopWords(stopWords)
        .build();

    LOGGER.info("Start training Paragraph Vectors");
    paragraphVectors.fit();
    LOGGER.info("Stop training Paragraph Vectors");
    return new VectorModel(paragraphVectors);
  }
}
