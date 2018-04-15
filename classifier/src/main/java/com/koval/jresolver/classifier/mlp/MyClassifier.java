package com.koval.jresolver.classifier.mlp;

import org.apache.commons.io.IOUtils;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyClassifier {

  private static final Logger LOGGER = LoggerFactory.getLogger(MyClassifier.class);

  //private static Map<Integer,String> classifiers = readEnumCSV("/classification/classes_0.csv");
  private static Map<Integer,String> classifiers = readEnumCSV("classes_0.csv");

  private int labelIndex;
  private int batchSizeTraining;
  private int batchSizeTest;
  private int classifierInputs;
  private int classifierOutputs;

  public MyClassifier(int labelIndex, int batchSizeTraining, int batchSizeTest, int classifierInputs, int classifierOutputs ) {
    this.labelIndex = labelIndex;
    this.batchSizeTraining = batchSizeTraining;
    this.batchSizeTest = batchSizeTest; // this is the data we want to classify
    this.classifierInputs = classifierInputs;
    this.classifierOutputs = classifierOutputs;
  }

  public void launch() {
    try {
      //DataSet trainingData = readCSVDataset("/classification/dataset.csv",
      DataSet trainingData = readCSVDataset("dataset.csv",
        batchSizeTraining, labelIndex, classifierOutputs);

      //DataSet testData = readCSVDataset("/classification/dataset.csv",
      DataSet testData = readCSVDataset("dataset.csv",
        batchSizeTest, labelIndex, classifierOutputs);

      logFeatureMatrix(testData);

      //We need to normalize our data. We'll use NormalizeStandardize (which gives us mean 0, unit variance):
      DataNormalization normalizer = new NormalizerStandardize();
      normalizer.fit(trainingData);           //Collect the statistics (mean/stdev) from the training data. This does not modify the input data
      normalizer.transform(trainingData);     //Apply normalization to the training data
      normalizer.transform(testData);         //Apply normalization to the test data. This is using statistics calculated from the *training* set

      int iterations = 1000;
      long seed = 6;

      LOGGER.info("Build model....");
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


      //Save the model
      File locationToSave = new File("MyMultiLayerNetwork.zip");      //Where to save the network. Note: the file is in .zip format - can be opened externally
      boolean saveUpdater = true;                                             //Updater: i.e., the state for Momentum, RMSProp, Adagrad etc. Save this if you want to train your network more in the future
      ModelSerializer.writeModel(model, locationToSave, saveUpdater);
      //Load the model
      //MultiLayerNetwork restored = ModelSerializer.restoreMultiLayerNetwork(locationToSave);



      //evaluate the model on the test set
      System.out.println("Evaluate model....");
      Evaluation eval = new Evaluation(classifierOutputs);
      INDArray output = model.output(testData.getFeatureMatrix());

     /* while(testIter.hasNext()){
        DataSet t = testIter.next();
        INDArray features = t.getFeatureMatrix();
        INDArray lables = t.getLabels();
        INDArray predicted = model.output(features,false);
        eval.eval(lables, predicted);
      }*/

      eval.eval(testData.getLabels(), output);
      LOGGER.info(eval.stats());


      for (int i = 0; i < output.rows() ; i++) {
        // set the classification from the fitted results
        System.out.println(classifiers.get(maxIndex(getFloatArrayFromSlice(output.slice(i)))));
      }
      logFeatureMatrix(testData);


    } catch (Exception e){
      e.printStackTrace();
    }
  }

  private void logFeatureMatrix(DataSet testData) {
    INDArray features = testData.getFeatureMatrix();
    for (int i = 0; i < features.rows(); i++) {
      INDArray slice = features.slice(i);
      for (int j = 0; j < features.columns(); j++) {
        System.out.print(String.valueOf(slice.getFloat(j)) + "\t ");
      }
      System.out.println();
    }
  }


  public static float[] getFloatArrayFromSlice(INDArray rowSlice){
    float[] result = new float[rowSlice.columns()];
    for (int i = 0; i < rowSlice.columns(); i++) {
      result[i] = rowSlice.getFloat(i);
    }
    return result;
  }

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

  public static Map<Integer,String> readEnumCSV(String csvFileClasspath) {
    try{
      //List<String> lines = IOUtils.readLines(new ClassPathResource(csvFileClasspath).getInputStream());
      List<String> lines = IOUtils.readLines(new FileInputStream(csvFileClasspath));
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
    //recordReader.initialize(new FileSplit(new ClassPathResource(csvFileClasspath).getFile()));
    recordReader.initialize(new FileSplit(new File(csvFileClasspath)));
    DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, batchSize, labelIndex, numClasses);
    return iterator.next();
  }
}
