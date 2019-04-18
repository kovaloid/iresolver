package com.koval.jresolver.common.api.util;

import java.util.ArrayList;
import java.util.List;


public final class CollectionsUtil {

  private CollectionsUtil() {
  }

  public static <T> List<T> convert(final Iterable<T> iterable) {
    List<T> result = new ArrayList<>();
    if (iterable == null) {
      return result;
    }
    iterable.forEach(result::add);
    return result;
  }
}
