package com.koval.jresolver.wizard.config.ext;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;


@SuppressWarnings("PMD")
public class OrderedProperties extends Properties {

  private Vector<Object> names;

  public OrderedProperties() {
    super();
    names = new Vector<>();
  }

  @Override
  public Enumeration<?> propertyNames() {
    return names.elements();
  }

  @Override
  public Object put(Object key, Object value) {
    if (names.contains(key)) {
      names.remove(key);
    }
    names.add(key);
    return super.put(key, value);
  }

  @Override
  public Object remove(Object key) {
    names.remove(key);
    return super.remove(key);
  }
}
