package com.koval.jresolver.classifier.core.impl;

import java.io.*;
import java.util.Collection;
import java.util.List;

import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.AbstractCache;
import org.deeplearning4j.text.documentiterator.LabelsSource;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareListSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareSentenceIterator;
import org.deeplearning4j.text.stopwords.StopWords;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import com.koval.jresolver.classifier.core.Vectorizer;


public class DocVectorizer implements Vectorizer {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocVectorizer.class);

  private ParagraphVectors paragraphVectors;
  private TokenPreProcess tokenPreprocessor;
  private List<String> stopWords;
  private ClassifierProperties classifierProperties;

  public DocVectorizer(ClassifierProperties classifierProperties) {
    this(new StemmingPreprocessor(), StopWords.getStopWords(), classifierProperties);
  }

  public DocVectorizer(TokenPreProcess tokenPreprocessor, List<String> stopWords, ClassifierProperties classifierProperties) {
    this.tokenPreprocessor = tokenPreprocessor;
    this.stopWords = stopWords;
    this.classifierProperties = classifierProperties;
  }

  @Override
  public void createFromFile(File inputFile) {
    try (InputStream inputStream = new FileInputStream(inputFile)) {
      createFromInputStream(inputStream);
    } catch (IOException e) {
      LOGGER.error("Could not find file: " + inputFile.getAbsolutePath(), e);
    }
  }

  public void createFromDataset(String dataSetFileName) throws IOException {
    try (InputStream inputStream = new FileInputStream(dataSetFileName)) {
      createFromInputStream(inputStream);
    } catch (IOException e) {
      LOGGER.error("Could not find data set: " + dataSetFileName, e);
      throw e;
    }
  }

  @Override
  public void createFromInputStream(InputStream inputStream) throws IOException {
    try {
      LabelAwareSentenceIterator iterator = new LabelAwareListSentenceIterator(inputStream, "|", 0, 1);
      createVectorizerWithIterator(iterator);
    } catch (IOException e) {
      LOGGER.error("Could not create vectorizer from stream", e);
      throw e;
    }
  }

  public void createFromResourceWithoutLabels(String classPathResource) {
    try {
      ClassPathResource resource = new ClassPathResource(classPathResource);
      File file = resource.getFile();
      SentenceIterator iterator = new BasicLineIterator(file);
      createVectorizerWithIterator(iterator);
    } catch (IOException e) {
      LOGGER.error("Could not create vectorizer from resource without labels", e);
    }
  }

  private void createVectorizerWithIterator(SentenceIterator iterator) {
    AbstractCache<VocabWord> cache = new AbstractCache<>();

    TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
    tokenizerFactory.setTokenPreProcessor(tokenPreprocessor);

    LabelsSource source = new LabelsSource("DOC_");

    paragraphVectors = new ParagraphVectors.Builder()
      .minWordFrequency(classifierProperties.getMinWordFrequency())
      .iterations(classifierProperties.getIterations())
      .epochs(classifierProperties.getEpochs())
      .layerSize(classifierProperties.getLayerSize())
      .learningRate(classifierProperties.getLearningRate())
      .labelsSource(source)
      .windowSize(classifierProperties.getWindowSize())
      .iterate(iterator)
      .trainWordVectors(classifierProperties.isTrainWordVectors())
      .vocabCache(cache)
      .tokenizerFactory(tokenizerFactory)
      .sampling(classifierProperties.getSampling())
      .stopWords(stopWords)
      .build();

    paragraphVectors.fit();
  }

  @Override
  public void save(String pathToSerializedFile) {
    WordVectorSerializer.writeParagraphVectors(paragraphVectors, pathToSerializedFile);
  }

  @Override
  public void load(String pathToSerializedFile) {
    try {
      paragraphVectors = WordVectorSerializer.readParagraphVectors(pathToSerializedFile);
      TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
      tokenizerFactory.setTokenPreProcessor(tokenPreprocessor);
      paragraphVectors.setTokenizerFactory(tokenizerFactory);
    } catch (IOException e) {
      LOGGER.error("Could not load paragraph vectors from file", e);
    }
  }

  public double getSimilarity(String label1, String label2) {
    return paragraphVectors.similarity(label1, label2);
  }

  @Override
  public Collection<String> getNearestLabels(String rawText, int topN) {
    return paragraphVectors.nearestLabels(rawText, topN);
  }

  public ParagraphVectors getParagraphVectors() {
    return paragraphVectors;
  }
}
