package com.koval.jresolver.connector.jira.util;

import java.util.ArrayList;
import java.util.Collection;


public final class CollectionsUtil {

  private CollectionsUtil() {
  }

  public static <T> Collection<T> convert(final Iterable<T> iterable) {
    Collection<T> result = new ArrayList<>();
    if (iterable == null) {
      return result;
    }
    iterable.forEach(result::add);
    return result;
  }
}
