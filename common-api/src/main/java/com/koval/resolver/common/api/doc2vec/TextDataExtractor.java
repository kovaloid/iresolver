package com.koval.resolver.common.api.doc2vec;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.util.TextUtil;


public class TextDataExtractor {

  public String extract(final Issue issue) {
    final StringBuilder textData = new StringBuilder(100);
    if (issue.getSummary() != null && !issue.getSummary().isEmpty()) {
      textData
          .append(TextUtil.simplify(issue.getSummary()))
          .append(' ');
    }
    if (issue.getDescription() != null && !issue.getDescription().isEmpty()) {
      textData
          .append(TextUtil.simplify(issue.getDescription()))
          .append(' ');
    }
    return textData.toString().trim();
  }
}
