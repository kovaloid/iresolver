package com.koval.jresolver.resolver;


import java.util.List;

public interface Consumer<T> {
  void consume(List<T> data);
}
