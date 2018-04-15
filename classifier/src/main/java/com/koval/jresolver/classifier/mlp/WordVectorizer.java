package com.koval.jresolver.classifier.mlp;

import com.koval.jresolver.classifier.StemmingPreprocessor;
import com.koval.jresolver.classifier.Vectorizer;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareFileSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareListSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareSentenceIterator;
import org.deeplearning4j.text.stopwords.StopWords;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.deeplearning4j.util.SerializationUtils;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class WordVectorizer implements Vectorizer {

  private final static Logger LOGGER = LoggerFactory.getLogger(WordVectorizer.class);

  private TfidfVectorizer vectorizer;
  private TokenPreProcess tokenPreprocessor;
  private int minWordFrequency;
  private Collection<String> stopWords;
  private final Map<Integer, List<String>> classes = new HashMap<>();

  public WordVectorizer() {
    this(new StemmingPreprocessor(), 1, StopWords.getStopWords());
  }

  public WordVectorizer(TokenPreProcess tokenPreprocessor, int minWordFrequency, Collection<String> stopWords) {
    this.tokenPreprocessor = tokenPreprocessor;
    this.minWordFrequency = minWordFrequency;
    this.stopWords = stopWords;
  }

  public void createFromFilesInFolder(String pathToDocuments) {
    try {
      File rootDir = new ClassPathResource(pathToDocuments).getFile();
      LabelAwareSentenceIterator iterator = new LabelAwareFileSentenceIterator(rootDir);
      createVectorizerWithIterator(iterator);
    } catch (IOException e) {
      LOGGER.error("Could not create vectorizer from documents", e);
    }
  }

  @Override
  public void createFromFile(File inputFile) {
    try (InputStream inputStream = new FileInputStream(inputFile)) {
      createFromInputStream(inputStream);
    } catch (IOException e) {
      LOGGER.error("Could not find file: " + inputFile.getAbsolutePath(), e);
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

  private void createVectorizerWithIterator(LabelAwareSentenceIterator iterator) {
    classes.clear();
    iterator.setPreProcessor((sentence) -> {
      String[] labels = iterator.currentLabel().split(",");
      for (int i = 0; i < labels.length; i++) {
        if (!classes.containsKey(i)) {
          classes.put(i, new ArrayList<>());
        }
        if (!classes.get(i).contains(labels[i])) {
          classes.get(i).add(labels[i]);
        }
      }
      return sentence;
    });
    TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
    tokenizerFactory.setTokenPreProcessor(tokenPreprocessor);

    vectorizer = new TfidfVectorizer.Builder().setMinWordFrequency(minWordFrequency)
      .setStopWords(stopWords)
      .setTokenizerFactory(tokenizerFactory)
      .setIterator(iterator)
      .allowParallelTokenization(false)
      .build();

    vectorizer.fit();
  }

  public void createFromFile(String pathToInputFile) {
    createFromFile(new File(pathToInputFile));
  }

  @Override
  public void save(String pathToSerializedFile) {
    SerializationUtils.saveObject(vectorizer, new File(pathToSerializedFile));
  }

  @Override
  public void load(String pathToSerializedFile) {
    vectorizer = SerializationUtils.readObject(new File(pathToSerializedFile));
    TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
    tokenizerFactory.setTokenPreProcessor(tokenPreprocessor);
    vectorizer.setTokenizerFactory(tokenizerFactory);
  }

  public double[] getVectorFromString(String document) {
    INDArray vector = vectorizer.transform(document);
    LOGGER.info("TF-IDF vector: " + Arrays.toString(vector.data().asDouble()));
    return vector.data().asDouble();
  }

  public double[] getVectorFromFile(File document) {
    LOGGER.info("Handle file: " + document.getName());
    try {
      return getVectorFromString(FileUtils.readFileToString(document, Charsets.UTF_8));
    } catch (IOException e) {
      LOGGER.error("Could not find file: " + document.getAbsolutePath(), e);
      return null;
    }
  }

  public TfidfVectorizer getVectorObject() {
    return vectorizer;
  }

  public List<VocabWord> getVocabularSortedByIndex() {
    return vectorizer.getVocabCache().vocabWords().stream()
      .sorted((vocabWord1, vocabWord2) -> {
          if (vocabWord1.getIndex() > vocabWord2.getIndex()) {
            return 1;
          } else if (vocabWord1.getIndex() < vocabWord2.getIndex()) {
            return -1;
          }
          return 0;
      })
      .collect(Collectors.toList());
  }

  public Map<Integer, List<String>> getClasses() {
    return classes;
  }
}
