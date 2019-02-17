package com.koval.jresolver.processor;

import java.util.List;

public interface Processor {
  void prepare();
  List<?> execute();
}
