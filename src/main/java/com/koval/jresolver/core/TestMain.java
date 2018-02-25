package com.koval.jresolver.core;

import java.io.File;


public class TestMain {
  public static void main(String[] args) {
    WordVectorizer wordVectorizer = new WordVectorizer();
    wordVectorizer.createFromFile("raw.txt");
    wordVectorizer.save("vectorizer.txt");
    wordVectorizer.load("vectorizer.txt");

    wordVectorizer.getVectorFromString("John likes");
    wordVectorizer.getVectorFromFile(new File("src/main/resources/tripledir/fileone"));
    System.out.println(wordVectorizer.getVocabularSortedByIndex());

    DataSetCreator dataSetCreator = new DataSetCreator(wordVectorizer);
    dataSetCreator.create("raw.txt", "dataset.csv");



    int batchSizeTraining = 30;
    int batchSizeTest = 44;

    int labelIndex = 4; //index of column with class id
    int classifierInputs = 4; //word vector length
    int classifierOutputs = 3; //number of classes

    MyClassifier cl = new MyClassifier(labelIndex, batchSizeTraining, batchSizeTest, classifierInputs, classifierOutputs);
    cl.launch();
  }
}
