package com.koval.resolver.common.api.vectorization;

import java.io.File;
import java.io.IOException;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import com.koval.resolver.common.api.configuration.component.VectorizerConfiguration;


public class VectorModelSerializer {

  private final VectorizerConfiguration properties;

  public VectorModelSerializer(final VectorizerConfiguration properties) {
    this.properties = properties;
  }

  public void serialize(final VectorModel vectorModel, final String file) {
    WordVectorSerializer.writeParagraphVectors(vectorModel.getVectorizer(), file);
  }

  public VectorModel deserialize(final File vectorModelFile, final String language) throws IOException {
    final ParagraphVectors vectorizer = WordVectorSerializer.readParagraphVectors(vectorModelFile);
    final TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
    final TokenPreProcess preProcessor = new StemmingPreprocessor().setLanguage(language);
    tokenizerFactory.setTokenPreProcessor(preProcessor);
    vectorizer.setTokenizerFactory(tokenizerFactory);
    return new VectorModel(vectorizer, properties.getTopResults());
  }

}
