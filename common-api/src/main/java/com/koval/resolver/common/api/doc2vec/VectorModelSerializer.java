package com.koval.resolver.common.api.doc2vec;

import java.io.File;
import java.io.IOException;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

public class VectorModelSerializer {

  public void serialize(final VectorModel vectorModel, final String file) {
    WordVectorSerializer.writeParagraphVectors(vectorModel.getParagraphVectors(), file);
  }

  public VectorModel deserialize(final File vectorModelFile, final String language) throws IOException {
    final ParagraphVectors paragraphVectors = WordVectorSerializer.readParagraphVectors(vectorModelFile);
    final TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
    final TokenPreProcess preProcessor = new StemmingPreprocessor().setLanguage(language);
    tokenizerFactory.setTokenPreProcessor(preProcessor);
    paragraphVectors.setTokenizerFactory(tokenizerFactory);
    return new VectorModel(paragraphVectors);
  }

}
