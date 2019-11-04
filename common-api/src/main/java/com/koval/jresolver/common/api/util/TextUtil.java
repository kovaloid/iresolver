package com.koval.jresolver.common.api.util;

public final class TextUtil {

  private TextUtil() {
  }

  public static String simplify(String text) {
    return text
        .trim()
        .replaceAll("[^A-Za-z0-9]", " ")
        .replaceAll(" +", " ");
  }
}
