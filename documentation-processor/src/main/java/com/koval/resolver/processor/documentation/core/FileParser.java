package com.koval.resolver.processor.documentation.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileParser {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocOutputFilesParser.class);

  private final FileRepository fileRepository;

  public FileParser(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  public <T> List<T> parseFile(
          final String fileName,
          final LineParser<T> lineParser
  ) {
    List<T> parsedClasses = new ArrayList<>();

    try (
            InputStream fileInputStream = fileRepository.readFile(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader)
    ) {
      parsedClasses = reader.lines()
              .map(lineParser::parseLine)
              .collect(Collectors.toList());
    } catch (IOException e) {
      LOGGER.error("Could not read file: " + fileName, e);
    }

    return parsedClasses;
  }
}
