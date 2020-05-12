package com.koval.resolver.common.api.util;

import java.util.Arrays;
import java.util.List;


public final class TextUtil {

  private static final List<String> QUESTION_WORDS = Arrays.asList("who", "what", "when", "where", "why", "which",
      "how", "is", "are", "am", "can", "could", "should", "have", "does", "do");

  private TextUtil() {
  }

  public static String simplify(final String text) {
    return text
        .trim()
        .replaceAll("[^A-Za-z0-9]", " ")
        .replaceAll(" +", " ");
  }

  public static boolean hasQuestion(final String text) {
    final long numberOfQuestionWords = QUESTION_WORDS.stream()
        .filter(text::contains)
        .count();
    return numberOfQuestionWords > 0 && text.contains("?");
  }
}
