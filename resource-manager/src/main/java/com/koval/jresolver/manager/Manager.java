package com.koval.jresolver.manager;

import java.io.File;

public final class Manager {

  private static final String DATASET_FILE_NAME = "DataSet.txt";
  private static final String VECTOR_MODEL_FILE_NAME = "VectorModel.zip";

  private static String workDir; // root of project
  private static String dataDir; // root/data

  private Manager() {
  }

  static {
    if (new File("settings.gradle").exists()) {  // settings.gradle always in root of project
      workDir = "";
    } else {
      workDir = "../"; //for subprojects and distribution
    }
    dataDir = workDir + "data/";
  }

  public static File getDataDirectory() {
    return new File(dataDir);
  }

  public static File getOutputDirectory() {
    return new File(workDir + "output/");
  }

  public static File getDataSet() {
    return new File(dataDir + DATASET_FILE_NAME);
  }

  public static File getVectorModel() {
    return new File(dataDir + VECTOR_MODEL_FILE_NAME);
  }

  public static File getOutputFile() {
    return new File(getOutputDirectory() + "/index.html");
  }
}
