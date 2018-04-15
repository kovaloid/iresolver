package com.koval.jresolver.classifier.doc2vec;


import com.koval.jresolver.connector.client.JiraClient;
import com.koval.jresolver.connector.client.impl.BasicJiraClient;

public class TestMain {

  public static void main(String[] args) throws Exception {
    DocVectorizer docVectorizer = new DocVectorizer();
    docVectorizer.createFromResourceWithoutLabels("/raw_sentences.txt");
    //docVectorizer.load("docVectors.dv");

    System.out.println(docVectorizer.getNearestLabels("This is my way .", 10));

    //Doc2vec is an extension of word2vec that
    //learns to correlate labels and words rather than words with other words.

    System.out.println("9836/12493 ('This is my house .'/'This is my world .') similarity: " + docVectorizer.getSimilarity("DOC_9835", "DOC_12492"));
    System.out.println("3721/16393 ('This is my way .'/'This is my work .') similarity: " + docVectorizer.getSimilarity("DOC_3720", "DOC_16392"));
    System.out.println("6348/3721 ('This is my case .'/'This is my way .') similarity: " + docVectorizer.getSimilarity("DOC_6347", "DOC_3720"));
    System.out.println("3721/9853 ('This is my way .'/'We now have one .') similarity: " + docVectorizer.getSimilarity("DOC_3720", "DOC_9852"));

  }
}
