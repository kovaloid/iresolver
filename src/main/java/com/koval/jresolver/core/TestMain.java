package com.koval.jresolver.core;


public class TestMain {
  public static void main(String[] args) {
    WordVectorizer wordVectorizer = new WordVectorizer();
    wordVectorizer.createFromFile("raw.txt");
    wordVectorizer.save("vectorizer.txt");
    wordVectorizer.load("vectorizer.txt");

    ClassesFileCreator classesFileCreator = new ClassesFileCreator(wordVectorizer);
    classesFileCreator.createFilesWithPrefix("classes");

    wordVectorizer.getVectorFromString("John likes");
    System.out.println(wordVectorizer.getVocabularSortedByIndex());

    DataSetCreator dataSetCreator = new DataSetCreator(wordVectorizer);
    dataSetCreator.create("raw.txt", "dataset.csv");

    int batchSizeTraining = 3; //30;
    int batchSizeTest = 5; //44;

    int labelIndex = 0; //4; //index of column with class id
    int classifierInputs = wordVectorizer.getVocabularSortedByIndex().size(); //4; //word vector length
    int classifierOutputs = wordVectorizer.getClasses().get(0).size(); //3; //number of classes

    MyClassifier cl = new MyClassifier(labelIndex, batchSizeTraining, batchSizeTest, classifierInputs, classifierOutputs);
    cl.launch();
  }
}
