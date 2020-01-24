package com.koval.jresolver.connector.confluence;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.configuration.bean.connectors.ConfluenceConnectorConfiguration;
import com.koval.jresolver.common.api.configuration.bean.processors.ConfluenceProcessorConfiguration;
import com.koval.jresolver.connector.confluence.client.ConfluenceClient;
import com.koval.jresolver.connector.confluence.core.ConfluenceDataSetWriter;
import com.koval.jresolver.connector.confluence.core.ConfluencePageReceiver;


public final class ConfluenceConnector {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfluenceConnector.class);

  private final ConfluenceConnectorConfiguration connectorProperties;
  private final ConfluenceProcessorConfiguration processorProperties;

  public ConfluenceConnector(ConfluenceConnectorConfiguration connectorProperties,
                             ConfluenceProcessorConfiguration processorProperties) {
    this.connectorProperties = connectorProperties;
    this.processorProperties = processorProperties;
  }

  public void createDataSet() {
    try (ConfluenceClient confluenceClient = new ConfluenceClient(connectorProperties);
         ConfluenceDataSetWriter confluenceDataSetWriter = new ConfluenceDataSetWriter(processorProperties)) {
      ConfluencePageReceiver receiver = new ConfluencePageReceiver(confluenceClient, connectorProperties);
      receiver.start(confluenceDataSetWriter);
    } catch (IOException e) {
      LOGGER.error("Could not create confluence data set", e);
    }
  }
}
