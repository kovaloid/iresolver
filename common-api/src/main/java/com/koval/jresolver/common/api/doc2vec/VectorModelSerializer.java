package com.koval.jresolver.common.api.doc2vec;

import java.io.File;
import java.io.IOException;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;


public class VectorModelSerializer {

  public void serialize(VectorModel vectorModel, String file) {
    WordVectorSerializer.writeParagraphVectors(vectorModel.getParagraphVectors(), file);
  }

  public VectorModel deserialize(File vectorModelFile, String language) throws IOException {
    ParagraphVectors paragraphVectors = WordVectorSerializer.readParagraphVectors(vectorModelFile);
    TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
    TokenPreProcess preProcessor = new StemmingPreprocessor().setLanguage(language);
    tokenizerFactory.setTokenPreProcessor(preProcessor);
    paragraphVectors.setTokenizerFactory(tokenizerFactory);
    return new VectorModel(paragraphVectors);
  }
}
