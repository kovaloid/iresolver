package com.koval.jresolver.docprocessor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface FileParser {
  Map<Integer, String> getMapping(InputStream input) throws IOException;
}
