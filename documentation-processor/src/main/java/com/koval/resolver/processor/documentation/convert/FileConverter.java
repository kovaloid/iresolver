package com.koval.resolver.processor.documentation.convert;

import com.koval.resolver.processor.documentation.bean.MediaType;

public interface FileConverter {
  void convert(String inputFilePath, String outputFilePath);

  MediaType getConvertibleType(MediaType docType);
}

