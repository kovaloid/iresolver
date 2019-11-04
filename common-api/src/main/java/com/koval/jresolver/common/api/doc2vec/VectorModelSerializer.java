package com.koval.jresolver.common.api.doc2vec;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import java.io.File;
import java.io.IOException;


public class VectorModelSerializer {

  private final TokenPreProcess preProcessor;
  private final String vectorModelPath;
  private final String vectorModelFileName;

  public VectorModelSerializer(Doc2VecProperties properties) {
    this.preProcessor = new StemmingPreprocessor().setLanguage(properties.getLanguage());
    this.vectorModelPath = properties.getWorkFolder();
    this.vectorModelFileName = properties.getVectorModelFileName();
  }

  public File serialize(VectorModel vectorModel) {
    File vectorModelFile = new File(vectorModelPath, vectorModelFileName);
    WordVectorSerializer.writeParagraphVectors(vectorModel.getParagraphVectors(), vectorModelFile);
    return vectorModelFile;
  }

  public VectorModel deserialize(File vectorModelFile) throws IOException {
    ParagraphVectors paragraphVectors = WordVectorSerializer.readParagraphVectors(vectorModelFile);
    TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
    tokenizerFactory.setTokenPreProcessor(preProcessor);
    paragraphVectors.setTokenizerFactory(tokenizerFactory);
    return new VectorModel(paragraphVectors);
  }

  public boolean isSerializedFileExist() {
    return getClass().getClassLoader().getResource(vectorModelFileName) != null;
  }
}
