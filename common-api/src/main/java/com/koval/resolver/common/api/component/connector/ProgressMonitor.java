package com.koval.resolver.common.api.component.connector;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;


public class ProgressMonitor {

  private final int batchSize;
  private final int finishIndex;

  private long totalStart;
  private long totalEnd;
  private long batchEnd;

  public ProgressMonitor(final int batchSize, final int finishIndex) {
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

  public String getFormattedRemainingTime(final int currentIndex) {
    final DateTime dateTime = getRemainingTime(currentIndex);
    return dateTime.toString("HH:mm:ss");
  }

  private DateTime getRemainingTime(final int currentIndex) {
    if (batchSize == 0) {
      return new DateTime(0).withZone(DateTimeZone.UTC);
    }
    final int iterations = currentIndex / batchSize;
    if (iterations == 0) {
      return new DateTime(0).withZone(DateTimeZone.UTC);
    }
    final long averageBatchTime = (batchEnd - totalStart) / iterations;
    return new DateTime(averageBatchTime * (finishIndex - currentIndex) / batchSize).withZone(DateTimeZone.UTC);
  }

  public String getFormattedSpentTime() {
    final DateTime dateTime = getSpentTime();
    return dateTime.toString("HH:mm:ss");
  }

  private DateTime getSpentTime() {
    return new DateTime(totalEnd - totalStart).withZone(DateTimeZone.UTC);
  }
}
