package com.koval.resolver.common.api.component.processor;

import java.io.Closeable;
import java.util.List;


public interface DataSetWriter<T> extends Closeable {

  void write(List<T> results);
}
