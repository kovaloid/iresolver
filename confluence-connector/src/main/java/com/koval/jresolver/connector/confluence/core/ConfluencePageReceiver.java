package com.koval.jresolver.connector.confluence.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.Space;
import com.atlassian.confluence.api.model.pagination.PageResponse;
import com.koval.jresolver.connector.confluence.client.ConfluenceClient;
import com.koval.jresolver.connector.confluence.configuration.ConfluenceConnectorProperties;


public class ConfluencePageReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfluencePageReceiver.class);

  private final ConfluenceClient client;
  private final ConfluenceConnectorProperties properties;

  public ConfluencePageReceiver(ConfluenceClient client, ConfluenceConnectorProperties properties) {
    this.client = client;
    this.properties = properties;
  }

  public void start(ConfluenceDataSetWriter dataSetWriter) {
    List<Space> spaces = client.getSpacesByKeys(properties.getSpaceKeys());
    int startIndex = 0;
    int limit = properties.getLimitPerRequest();
    while (true) {
      PageResponse<Content> contentResponse = client.getPagesBySpaceKeys(spaces, startIndex, limit);
      LOGGER.info("Fetched confluence pages: {}", contentResponse.size());
      dataSetWriter.write(contentResponse.getResults());

      startIndex += contentResponse.size();
      if (!contentResponse.hasMore()) {
        break;
      }
    }
  }

}
