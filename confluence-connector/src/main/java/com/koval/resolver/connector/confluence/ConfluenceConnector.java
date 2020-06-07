package com.koval.resolver.connector.confluence;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.bean.confluence.ConfluencePage;
import com.koval.resolver.common.api.component.processor.DataSetWriter;
import com.koval.resolver.common.api.configuration.bean.connectors.ConfluenceConnectorConfiguration;
import com.koval.resolver.connector.confluence.client.ConfluenceClient;
import com.koval.resolver.connector.confluence.core.ConfluencePageReceiver;


public final class ConfluenceConnector {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfluenceConnector.class);

  private final ConfluenceConnectorConfiguration connectorProperties;

  public ConfluenceConnector(final ConfluenceConnectorConfiguration connectorProperties) {
    this.connectorProperties = connectorProperties;
  }

  public void createDataSet(final DataSetWriter<ConfluencePage> confluenceDataSetWriter) {
    try (ConfluenceClient confluenceClient = new ConfluenceClient(connectorProperties)) {
      final ConfluencePageReceiver receiver = new ConfluencePageReceiver(confluenceClient, connectorProperties);
      receiver.start(confluenceDataSetWriter);
    } catch (IOException e) {
      LOGGER.error("Could not create confluence data set", e);
    }
  }
}
