package com.koval.jresolver.core;


import org.apache.commons.io.IOUtils;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyClassifier {


  private static Logger log = LoggerFactory.getLogger(MyClassifier.class);

  private static Map<Integer,String> eats = readEnumCSV("/DataExamples/animals/eats.csv");
  private static Map<Integer,String> sounds = readEnumCSV("/DataExamples/animals/sounds.csv");
  private static Map<Integer,String> classifiers = readEnumCSV("/DataExamples/animals/classifiers.csv");

  private int labelIndex;
  private int batchSizeTraining;
  private int batchSizeTest;
  private int classifierInputs;
  private int classifierOutputs;

  public MyClassifier(int labelIndex, int batchSizeTraining, int batchSizeTest, int classifierInputs, int classifierOutputs ) {
    this.labelIndex = labelIndex; //5 values in each row of the animals.csv CSV: 4 input features followed by an integer label (class) index. Labels are the 5th value (index 4) in each row
    this.batchSizeTraining = batchSizeTraining; //Iris data set: 150 examples total. We are loading all of them into one DataSet (not recommended for large data sets)
    this.batchSizeTest = batchSizeTest; // this is the data we want to classify
    this.classifierInputs = classifierInputs;
    this.classifierOutputs = classifierOutputs;
  }

  public void launch() {
    try {
      DataSet trainingData = readCSVDataset(
        "/DataExamples/animals/animals_train.csv",
        batchSizeTraining, labelIndex, classifierOutputs);

      DataSet testData = readCSVDataset("/DataExamples/animals/animals.csv",
        batchSizeTest, labelIndex, classifierOutputs);


      // make the data model for records prior to normalization, because it
      // changes the data.
      Map<Integer,Map<String,Object>> animals = makeAnimalsForTesting(testData);

      //We need to normalize our data. We'll use NormalizeStandardize (which gives us mean 0, unit variance):
      DataNormalization normalizer = new NormalizerStandardize();
      normalizer.fit(trainingData);           //Collect the statistics (mean/stdev) from the training data. This does not modify the input data
      normalizer.transform(trainingData);     //Apply normalization to the training data
      normalizer.transform(testData);         //Apply normalization to the test data. This is using statistics calculated from the *training* set

      //int classifierInputs = 4; //vector of words
      //int classifierOutputs = 3; //vector of classes
      int iterations = 1000;
      long seed = 6;

      log.info("Build model....");
      MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
        .seed(seed)
        .iterations(iterations)
        .activation(Activation.TANH)
        .weightInit(WeightInit.XAVIER)
        .learningRate(0.1)
        .regularization(true).l2(1e-4)
        .list()
        .layer(0, new DenseLayer.Builder().nIn(classifierInputs).nOut(3).build())
        .layer(1, new DenseLayer.Builder().nIn(3).nOut(3).build())
        .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
          .activation(Activation.SOFTMAX).nIn(3).nOut(classifierOutputs).build())
        .backprop(true).pretrain(false)
        .build();

/*
      MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
        .seed(seed)
        .iterations(1)
        .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
        .learningRate(learningRate)
        .updater(Updater.NESTEROVS)     //To configure: .updater(new Nesterovs(0.9))
        .list()
        .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(numHiddenNodes)
          .weightInit(WeightInit.XAVIER)
          .activation(Activation.RELU)
          .build())
        .layer(1, new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD)
          .weightInit(WeightInit.XAVIER)
          .activation(Activation.SOFTMAX).weightInit(WeightInit.XAVIER)
          .nIn(numHiddenNodes).nOut(numOutputs).build())
        .pretrain(false).backprop(true).build();
      */


      //run the model
      MultiLayerNetwork model = new MultiLayerNetwork(conf);
      model.init();
      model.setListeners(new ScoreIterationListener(100)); //Print score every 100 parameter updates

      model.fit(trainingData);

      //evaluate the model on the test set
      System.out.println("Evaluate model....");
      Evaluation eval = new Evaluation(classifierOutputs); //vector of classes
      INDArray output = model.output(testData.getFeatureMatrix());

     /* while(testIter.hasNext()){
        DataSet t = testIter.next();
        INDArray features = t.getFeatureMatrix();
        INDArray lables = t.getLabels();
        INDArray predicted = model.output(features,false);

        eval.eval(lables, predicted);

      }*/

      eval.eval(testData.getLabels(), output);
      log.info(eval.stats());

      setFittedClassifiers(output, animals);
      logAnimals(animals);

    } catch (Exception e){
      e.printStackTrace();
    }
  }



  public static void logAnimals(Map<Integer,Map<String,Object>> animals){
    for(Map<String,Object> a:animals.values())
      log.info(a.toString());
  }

  public static void setFittedClassifiers(INDArray output, Map<Integer,Map<String,Object>> animals){
    for (int i = 0; i < output.rows() ; i++) {

      // set the classification from the fitted results
      animals.get(i).put("classifier",
        classifiers.get(maxIndex(getFloatArrayFromSlice(output.slice(i)))));

    }

  }


  /**
   * This method is to show how to convert the INDArray to a float array. This is to
   * provide some more examples on how to convert INDArray to types that are more java
   * centric.
   *
   * @param rowSlice
   * @return
   */
  public static float[] getFloatArrayFromSlice(INDArray rowSlice){
    float[] result = new float[rowSlice.columns()];
    for (int i = 0; i < rowSlice.columns(); i++) {
      result[i] = rowSlice.getFloat(i);
    }
    return result;
  }

  /**
   * find the maximum item index. This is used when the data is fitted and we
   * want to determine which class to assign the test row to
   *
   * @param vals
   * @return
   */
  public static int maxIndex(float[] vals){
    int maxIndex = 0;
    for (int i = 1; i < vals.length; i++){
      float newnumber = vals[i];
      if ((newnumber > vals[maxIndex])){
        maxIndex = i;
      }
    }
    return maxIndex;
  }

  /**
   * take the dataset loaded for the matric and make the record model out of it so
   * we can correlate the fitted classifier to the record.
   *
   * @param testData
   * @return
   */
  public static Map<Integer,Map<String,Object>> makeAnimalsForTesting(DataSet testData){
    Map<Integer,Map<String,Object>> animals = new HashMap<>();

    INDArray features = testData.getFeatureMatrix();
    for (int i = 0; i < features.rows() ; i++) {
      INDArray slice = features.slice(i);
      Map<String,Object> animal = new HashMap();

      //set the attributes
      animal.put("yearsLived", slice.getInt(0));
      animal.put("eats", eats.get(slice.getInt(1)));
      animal.put("sounds", sounds.get(slice.getInt(2)));
      animal.put("weight", slice.getFloat(3));

      animals.put(i,animal);
    }
    return animals;

  }


  public static Map<Integer,String> readEnumCSV(String csvFileClasspath) {
    try{
      List<String> lines = IOUtils.readLines(new ClassPathResource(csvFileClasspath).getInputStream());
      Map<Integer,String> enums = new HashMap<>();
      for(String line:lines){
        String[] parts = line.split(",");
        enums.put(Integer.parseInt(parts[0]),parts[1]);
      }
      return enums;
    } catch (Exception e){
      e.printStackTrace();
      return null;
    }

  }

  private static DataSet readCSVDataset(String csvFileClasspath, int batchSize, int labelIndex, int numClasses) throws IOException, InterruptedException {
    RecordReader recordReader = new CSVRecordReader();
    recordReader.initialize(new FileSplit(new ClassPathResource(csvFileClasspath).getFile()));
    DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, batchSize, labelIndex, numClasses);
    return iterator.next();
  }
}
