package com.koval.jresolver.docprocessor.similarity.core.model;

import java.io.File;
import java.io.IOException;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import com.koval.jresolver.docprocessor.similarity.configuration.DocumentationSimilarityProcessorProperties;


public class VectorModelSerializer {

  private final TokenPreProcess preProcessor;
  private final String vectorModelPath;
  private final String vectorModelFileName;

  public VectorModelSerializer(DocumentationSimilarityProcessorProperties similarityProcessorProperties) {
    this.preProcessor = new StemmingPreprocessor().setLanguage(similarityProcessorProperties.getLanguage());
    this.vectorModelPath = similarityProcessorProperties.getWorkFolder();
    this.vectorModelFileName = similarityProcessorProperties.getVectorModelFileName();
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
