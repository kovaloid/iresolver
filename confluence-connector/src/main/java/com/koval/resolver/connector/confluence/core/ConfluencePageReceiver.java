package com.koval.resolver.connector.confluence.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.Space;
import com.atlassian.confluence.api.model.pagination.PageResponse;
import com.koval.resolver.common.api.bean.confluence.ConfluencePage;
import com.koval.resolver.common.api.component.processor.DataSetWriter;
import com.koval.resolver.common.api.configuration.bean.connectors.ConfluenceConnectorConfiguration;
import com.koval.resolver.connector.confluence.client.ConfluenceClient;
import com.koval.resolver.connector.confluence.client.ConfluenceTransformer;


public class ConfluencePageReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfluencePageReceiver.class);

  private final ConfluenceClient client;
  private final ConfluenceConnectorConfiguration properties;

  public ConfluencePageReceiver(final ConfluenceClient client, final ConfluenceConnectorConfiguration properties) {
    this.client = client;
    this.properties = properties;
  }

  public void start(final DataSetWriter<ConfluencePage> dataSetWriter) {
    final ConfluenceTransformer transformer = new ConfluenceTransformer();
    final List<Space> spaces = client.getSpacesByKeys(properties.getSpaceKeys());
    int startIndex = 0;
    final int limit = properties.getLimitPerRequest();
    while (true) {
      final PageResponse<Content> contentResponse = client.getPagesBySpaceKeys(spaces, startIndex, limit);
      LOGGER.info("Fetched confluence pages: {}", contentResponse.size());
      dataSetWriter.write(transformer.transform(contentResponse.getResults()));

      startIndex += contentResponse.size();
      if (!contentResponse.hasMore()) {
        break;
      }
    }
  }

}
