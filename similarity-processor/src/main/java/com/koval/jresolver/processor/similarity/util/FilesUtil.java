package com.koval.jresolver.processor.similarity.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FilesUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(FilesUtil.class);

  private FilesUtil() {
  }

  public static void createFolder(File folder) {
    if (folder.exists()) {
      LOGGER.warn("Folder {} is already exist.", folder.getAbsolutePath());
    } else {
      if (folder.mkdirs()) {
        LOGGER.info("Folder {} was created successfully.", folder.getAbsolutePath());
      } else {
        LOGGER.error("Could not create folder {}.", folder.getAbsolutePath());
      }
    }
  }

  public static void createFile(File file) throws IOException {
    createFolder(file.getParentFile());
    if (file.exists()) {
      LOGGER.warn("Folder {} is already exist.", file.getAbsolutePath());
    } else {
      if (file.createNewFile()) {
        LOGGER.info("Folder {} was created successfully.", file.getAbsolutePath());
      } else {
        LOGGER.error("Could not create folder {}.", file.getAbsolutePath());
      }
    }
  }
}
