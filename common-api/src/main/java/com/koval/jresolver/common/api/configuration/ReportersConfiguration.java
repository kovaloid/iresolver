package com.koval.jresolver.common.api.configuration;

import java.util.Map;

public class ReportersConfiguration {
  private Map<Object, Object> html;
  private Map<Object, Object> text;

  public Map<Object, Object> getHtml() {
    return html;
  }

  public void setHtml(Map<Object, Object> html) {
    this.html = html;
  }

  public Map<Object, Object> getText() {
    return text;
  }

  public void setText(Map<Object, Object> text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "ReportersConfiguration{"
        + "html=" + html
        + ", text=" + text
        + '}';
  }
}
