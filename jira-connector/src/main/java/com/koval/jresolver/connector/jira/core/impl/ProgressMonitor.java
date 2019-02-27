package com.koval.jresolver.connector.jira.core.impl;

import org.joda.time.DateTime;


public class ProgressMonitor {

  private final int batchSize;
  private final int finishIndex;

  private long start;
  private long end;

  public ProgressMonitor(int batchSize, int finishIndex) {
    this.batchSize = batchSize;
    this.finishIndex = finishIndex;
  }

  public void startMeasuringTime() {
    start = new DateTime().getMillis();
  }

  public void endMeasuringTime() {
    end = new DateTime().getMillis();
  }

  public String getFormattedRemainingTime(int currentIndex) {
    DateTime dateTime = getRemainingTime(currentIndex);
    return dateTime.toString("HH:mm:ss");
  }

  public DateTime getRemainingTime(int currentIndex) {
    return new DateTime((end - start) * (finishIndex - currentIndex) / batchSize);
  }
}
