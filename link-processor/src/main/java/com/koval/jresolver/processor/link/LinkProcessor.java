package com.koval.jresolver.processor.link;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.jresolver.common.api.component.processor.IssueProcessor;
import com.koval.jresolver.processor.link.configuration.LinkProcessorProperties;


public class LinkProcessor implements IssueProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(LinkProcessor.class);

  private final LinkProcessorProperties properties;

  public LinkProcessor(LinkProcessorProperties properties) {
    this.properties = properties;
  }

  @Override
  public void run(Issue issue, IssueAnalysingResult result) {
    issue.getIssueFields().forEach(field -> {
      if (field.getId().equals(properties.getIssueField())) {
        LOGGER.info("External link: {}/{}", properties.getTargetLink(), field.getValue());
      }
    });
  }
}
