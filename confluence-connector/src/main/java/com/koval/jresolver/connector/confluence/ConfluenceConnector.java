package com.koval.jresolver.connector.confluence;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.connector.confluence.client.ConfluenceClient;
import com.koval.jresolver.connector.confluence.configuration.ConfluenceConnectorProperties;
import com.koval.jresolver.connector.confluence.core.ConfluenceDataSetWriter;
import com.koval.jresolver.connector.confluence.core.ConfluencePageReceiver;


public final class ConfluenceConnector {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfluenceConnector.class);

  private final ConfluenceConnectorProperties connectorProperties;

  public ConfluenceConnector(ConfluenceConnectorProperties connectorProperties) {
    this.connectorProperties = connectorProperties;
  }

  public void createDataSet() {
    try (ConfluenceClient confluenceClient = new ConfluenceClient(connectorProperties);
         ConfluenceDataSetWriter confluenceDataSetWriter = new ConfluenceDataSetWriter(connectorProperties)) {
      ConfluencePageReceiver receiver = new ConfluencePageReceiver(confluenceClient, connectorProperties);
      receiver.start(confluenceDataSetWriter);
    } catch (IOException e) {
      LOGGER.error("Could not create confluence data set", e);
    }
  }
}
