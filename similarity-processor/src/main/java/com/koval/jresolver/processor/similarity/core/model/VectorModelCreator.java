package com.koval.jresolver.processor.similarity.core.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.deeplearning4j.models.embeddings.learning.impl.sequence.DBOW;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.sequencevectors.enums.ListenerEvent;
import org.deeplearning4j.models.sequencevectors.listeners.SimilarityListener;
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

import com.koval.jresolver.processor.similarity.configuration.SimilarityProcessorProperties;


public class VectorModelCreator {

  private static final Logger LOGGER = LoggerFactory.getLogger(VectorModelCreator.class);

  private TokenPreProcess tokenPreprocessor;
  private List<String> stopWords;
  private SimilarityProcessorProperties similarityProcessorProperties;

  public VectorModelCreator(SimilarityProcessorProperties similarityProcessorProperties) {
    this(new StemmingPreprocessor().setLanguage(similarityProcessorProperties.getLanguage()), StopWords.getStopWords(),
        similarityProcessorProperties);
  }

  public VectorModelCreator(TokenPreProcess tokenPreprocessor, List<String> stopWords, SimilarityProcessorProperties similarityProcessorProperties) {
    this.tokenPreprocessor = tokenPreprocessor;
    this.stopWords = stopWords;
    this.similarityProcessorProperties = similarityProcessorProperties;
  }

  public VectorModel createFromFile(File inputFile) throws IOException {
    try (InputStream inputStream = new FileInputStream(inputFile)) {
      return createFromInputStream(inputStream);
    }
  }

  public VectorModel createFromResource(String dataSetFileName) throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(dataSetFileName)) {
      return createFromInputStream(inputStream);
    }
  }

  public VectorModel createFromInputStream(InputStream inputStream) throws IOException {
    LabelAwareSentenceIterator iterator = new LabelAwareListSentenceIterator(inputStream, "|", 0, 1);
    return createVectorModelWithIterator(iterator);
  }

  private VectorModel createVectorModelWithIterator(LabelAwareSentenceIterator iterator) {
    AbstractCache<VocabWord> cache = new AbstractCache<>();

    TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
    tokenizerFactory.setTokenPreProcessor(tokenPreprocessor);

    ParagraphVectors paragraphVectors = new ParagraphVectors.Builder()
        .sequenceLearningAlgorithm(new DBOW<>())
        .setVectorsListeners(Arrays.asList(
            new SimilarityListener<>(ListenerEvent.EPOCH, 1, "incomplet", "alloc"),
            new SimilarityListener<>(ListenerEvent.EPOCH, 1, "fear", "truststorepassword")))
        .minWordFrequency(similarityProcessorProperties.getMinWordFrequency())
        .iterations(similarityProcessorProperties.getIterations())
        .epochs(similarityProcessorProperties.getEpochs())
        .layerSize(similarityProcessorProperties.getLayerSize())
        .learningRate(similarityProcessorProperties.getLearningRate())
        .windowSize(similarityProcessorProperties.getWindowSize())
        .trainWordVectors(similarityProcessorProperties.isTrainWordVectors())
        .sampling(similarityProcessorProperties.getSampling())
        .iterate(iterator)
        .vocabCache(cache)
        .tokenizerFactory(tokenizerFactory)
        .stopWords(stopWords)
        .build();

    LOGGER.info("Start creating vector model");
    paragraphVectors.fit();
    LOGGER.info("Stop creating vector model");
    return new VectorModel(paragraphVectors);
  }
}
