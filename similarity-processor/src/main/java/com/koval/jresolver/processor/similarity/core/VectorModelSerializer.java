package com.koval.jresolver.processor.similarity.core;

import java.io.File;
import java.io.IOException;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import com.koval.jresolver.shared.Constants;


class VectorModelSerializer {

  File serialize(VectorModel vectorModel) {
    File vectorModelFile = new File(Constants.DATA_PATH, Constants.VECTOR_MODEL_FILE);
    WordVectorSerializer.writeParagraphVectors(vectorModel.getParagraphVectors(), vectorModelFile);
    return vectorModelFile;
  }

  VectorModel deserialize(File vectorModelFile) throws IOException {
    ParagraphVectors paragraphVectors = WordVectorSerializer.readParagraphVectors(vectorModelFile);
    TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
    tokenizerFactory.setTokenPreProcessor(new StemmingPreprocessor());
    paragraphVectors.setTokenizerFactory(tokenizerFactory);
    return new VectorModel(paragraphVectors);
  }

  boolean isSerializedFileExist() {
    return getClass().getClassLoader().getResource(Constants.VECTOR_MODEL_FILE) != null;
  }
}
