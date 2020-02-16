package com.koval.resolver.processor.documentation.split;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


public interface PageSplitter {

  Map<Integer, String> getMapping(InputStream input) throws IOException;
}
