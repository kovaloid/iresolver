package com.koval.resolver.common.api.bean.result;

import java.util.Objects;


public class Pair<E, M> {

  private final E entity;
  private M metric;

  public Pair(final E entity, final M metric) {
    this.entity = entity;
    this.metric = metric;
  }

  public E getEntity() {
    return entity;
  }

  public M getMetric() {
    return metric;
  }

  public void setMetric(final M metric) {
    this.metric = metric;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o instanceof Pair) {
      final Pair<?, ?> oP = (Pair<?, ?>)o;
      return (Objects.equals(entity, oP.entity))
          && (Objects.equals(metric, oP.metric));
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    int result = entity == null ? 0 : entity.hashCode();
    final int h = metric == null ? 0 : metric.hashCode();
    result = 37 * result + h ^ (h >>> 16);
    return result;
  }

  @Override
  public String toString() {
    return "[" + entity + ", " + metric + "]";
  }
}
