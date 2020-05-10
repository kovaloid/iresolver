package com.koval.resolver.processor.documentation.core;

public interface LineParser<T> {
  T parseLine(String line);
}
