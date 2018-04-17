package com.koval.jresolver.classifier.impl;

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

import com.koval.jresolver.classifier.Vectorizer;


public class DocVectorizer implements Vectorizer {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocVectorizer.class);

  private ParagraphVectors paragraphVectors;
  private TokenPreProcess tokenPreprocessor;
  private int minWordFrequency;
  private List<String> stopWords;

  public DocVectorizer() {
    this(new StemmingPreprocessor(), 1, StopWords.getStopWords());
  }

  public DocVectorizer(TokenPreProcess tokenPreprocessor, int minWordFrequency, List<String> stopWords) {
    this.tokenPreprocessor = tokenPreprocessor;
    this.minWordFrequency = minWordFrequency;
    this.stopWords = stopWords;
  }

  @Override
  public void createFromFile(File inputFile) {
    try (InputStream inputStream = new FileInputStream(inputFile)) {
      createFromInputStream(inputStream);
    } catch (IOException e) {
      LOGGER.error("Could not find file: " + inputFile.getAbsolutePath(), e);
    }
  }

  public void createFromDataset(String datasetFileName) {
    try (InputStream inputStream = DocVectorizer.class.getResourceAsStream(datasetFileName)) {
      createFromInputStream(inputStream);
    } catch (IOException e) {
      LOGGER.error("Could not find dataset: " + datasetFileName, e);
    }
  }

  @Override
  public void createFromInputStream(InputStream inputStream) {
    try {
      LabelAwareSentenceIterator iterator = new LabelAwareListSentenceIterator(inputStream, "|", 0, 1);
      createVectorizerWithIterator(iterator);
    } catch (IOException e) {
      LOGGER.error("Could not create vectorizer from stream", e);
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
      .minWordFrequency(minWordFrequency)
      .iterations(5)
      .epochs(1)
      .layerSize(100)
      .learningRate(0.025)
      .labelsSource(source)
      .windowSize(5)
      .iterate(iterator)
      .trainWordVectors(false)
      .vocabCache(cache)
      .tokenizerFactory(tokenizerFactory)
      .sampling(0)
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

  public Collection<String> getNearestLabels(String rawText, int topN) {
    return paragraphVectors.nearestLabels(rawText, topN);
  }

  public ParagraphVectors getParagraphVectors() {
    return paragraphVectors;
  }
}
