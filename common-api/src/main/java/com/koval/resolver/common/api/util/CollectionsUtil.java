package com.koval.resolver.common.api.util;

import java.util.ArrayList;
import java.util.List;


public final class CollectionsUtil {

  private CollectionsUtil() {
  }

  public static <T> List<T> convert(final Iterable<T> iterable) {
    final List<T> result = new ArrayList<>();
    if (iterable == null) {
      return result;
    }
    iterable.forEach(result::add);
    return result;
  }

  public static <T> T getLastItem(final List<T> list) throws IndexOutOfBoundsException {
    return list.subList(list.size() - 1, list.size()).get(0);
  }
}
