package com.koval.jresolver.common.api.bean.result;

import java.util.Objects;


public class Pair<K, V> {

  private final K key;
  private final V value;

  public Pair(K k, V v) {
    key = k;
    value = v;
  }

  public K getKey() {
    return key;
  }

  public V getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Pair)) {
      return false;
    } else {
      Pair<?, ?> oP = (Pair<?, ?>) o;
      return (Objects.equals(key, oP.key)) &&
          (Objects.equals(value, oP.value));
    }
  }

  @Override
  public int hashCode() {
    int result = key == null ? 0 : key.hashCode();
    final int h = value == null ? 0 : value.hashCode();
    result = 37 * result + h ^ (h >>> 16);
    return result;
  }

  @Override
  public String toString() {
    return "[" + key + ", " + value + "]";
  }
}
