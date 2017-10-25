package com.koval.jresolver.resolver;


import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface Consumer<T> {
  void consume(List<T> data);
  AtomicInteger getStatus();
}
