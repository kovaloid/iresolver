package com.koval.jresolver.manager;

import java.io.File;

public final class Manager {
  private static String workdir;

  private Manager() {
  }

  static {
    if (new File("build.gradle").exists()) {
      workdir = "resources/main/";
    } else {
      workdir = "../";
    }
  }

  public static void setTest() {
    workdir = "resources/test/";
  }

  public static String getResourceDirectory() {
    return workdir;
  }

  public static String getRulesDirectory() {
    return workdir + "rules/";
  }

  public static String getConfigDirectory() {
    return workdir + "config/";
  }

  public static String getDataDirectory() {
    return workdir + "data/";
  }

  public static String getOutputDirectory() {
    return workdir + "output/";
  }

  public static boolean checkReportGeneratorConfigure() {
    return new File(Manager.getDataDirectory()).exists();
  }

  public static boolean checkClassifierPrepare() {
    return new File(Manager.getDataDirectory() + "DataSet.txt").exists();
  }

  public static boolean checkClassifierConfigure() {
    return new File(Manager.getDataDirectory() + "VectorModel.zip").exists();
  }
}
