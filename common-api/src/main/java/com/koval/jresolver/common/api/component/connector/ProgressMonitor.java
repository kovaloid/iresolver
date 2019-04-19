package com.koval.jresolver.common.api.component.connector;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;


public class ProgressMonitor {

  private final int batchSize;
  private final int finishIndex;

  private long totalStart;
  private long totalEnd;
  private long batchEnd;

  public ProgressMonitor(int batchSize, int finishIndex) {
    this.batchSize = batchSize;
    this.finishIndex = finishIndex;
  }

  public void startMeasuringTotalTime() {
    totalStart = new DateTime().getMillis();
  }

  public void endMeasuringTotalTime() {
    totalEnd = new DateTime().getMillis();
  }

  public void endMeasuringBatchTime() {
    batchEnd = new DateTime().getMillis();
  }

  public String getFormattedRemainingTime(int currentIndex) {
    DateTime dateTime = getRemainingTime(currentIndex);
    return dateTime.toString("HH:mm:ss");
  }

  private DateTime getRemainingTime(int currentIndex) {
    if (batchSize == 0) {
      return new DateTime(0).withZone(DateTimeZone.UTC);
    }
    int iterations = currentIndex / batchSize;
    if (iterations == 0) {
      return new DateTime(0).withZone(DateTimeZone.UTC);
    }
    long averageBatchTime = (batchEnd - totalStart) / iterations;
    return new DateTime(averageBatchTime * (finishIndex - currentIndex) / batchSize).withZone(DateTimeZone.UTC);
  }

  public String getFormattedSpentTime() {
    DateTime dateTime = getSpentTime();
    return dateTime.toString("HH:mm:ss");
  }

  private DateTime getSpentTime() {
    return new DateTime(totalEnd - totalStart).withZone(DateTimeZone.UTC);
  }
}
