package com.koval.jresolver.rules;

import java.util.ArrayList;
import java.util.List;

public class RulesResult {
  private List<String> advices = new ArrayList<>();

  public List<String> getAdvices() {
    return advices;
  }

  public void putAdvice(String advice) {
    advices.add(advice);
  }

  public void clearAdvices() {
    this.advices.clear();
  }

  @Override
  public String toString() {
    return "RulesResult{" +
        "advices=" + advices +
        '}';
  }
}
