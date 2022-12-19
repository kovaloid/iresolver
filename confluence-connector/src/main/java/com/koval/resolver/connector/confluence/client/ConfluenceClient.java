package com.koval.resolver.connector.confluence.client;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.confluence.api.model.Expansion;
import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.ContentType;
import com.atlassian.confluence.api.model.content.Space;
import com.atlassian.confluence.api.model.pagination.PageRequest;
import com.atlassian.confluence.api.model.pagination.PageResponse;
import com.atlassian.confluence.api.model.pagination.PageResponseImpl;
import com.atlassian.confluence.api.model.pagination.SimplePageRequest;
import com.atlassian.confluence.rest.client.RemoteContentService;
import com.atlassian.confluence.rest.client.RemoteContentServiceImpl;
import com.atlassian.confluence.rest.client.RemoteSpaceService;
import com.atlassian.confluence.rest.client.RemoteSpaceServiceImpl;
import com.atlassian.confluence.rest.client.RestClientFactory;
import com.atlassian.confluence.rest.client.authentication.AuthenticatedWebResourceProvider;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.koval.resolver.common.api.auth.Credentials;
import com.koval.resolver.common.api.configuration.component.connectors.ConfluenceConnectorConfiguration;
import com.sun.jersey.api.client.Client;


public class ConfluenceClient implements Closeable {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfluenceClient.class);

  private final ListeningExecutorService executor;
  private final AuthenticatedWebResourceProvider provider;

  public ConfluenceClient(final ConfluenceConnectorConfiguration connectorProperties) {
    final Client client = RestClientFactory.newClient();
    executor = MoreExecutors.newDirectExecutorService();
    provider = new AuthenticatedWebResourceProvider(client, connectorProperties.getUrl(), "");
    if (!connectorProperties.isAnonymous()) {
      final Credentials credentials = Credentials.getCredentials(connectorProperties.getCredentialsFolder());
      provider.setAuthContext(credentials.getUsername(), credentials.getPassword().toCharArray());
    }
    LOGGER.info("Confluence client created for {}", connectorProperties.getUrl());
  }

  public List<Space> getSpacesByKeys(final List<String> spaceKeys) {
    final RemoteSpaceService spaceService = new RemoteSpaceServiceImpl(provider, executor);
    final PageRequest pageRequest = new SimplePageRequest(0, 1000);
    LOGGER.info("Get spaces by keys: {} with page request: {}", spaceKeys, pageRequest);
    return spaceService
        .find()
        .withKeys(spaceKeys.toArray(new String[spaceKeys.size()]))
        .fetchMany(pageRequest)
        .claim()
        .getResults();
  }

  public PageResponse<Content> getPagesBySpaceKeys(final List<Space> spaces, final int startIndex, final int limit) {
    final RemoteContentService contentService = new RemoteContentServiceImpl(provider, executor);
    // TODO fix limit configuration in requests and replace 100 with limit argument below
    final PageRequest pageRequest = new SimplePageRequest(startIndex, 100);
    LOGGER.info("Get pages by spaces: {} starting from: {} with batch size: {}",
        spaces.stream().map(Space::getKey).collect(Collectors.toList()), startIndex, limit);
    try {
      return contentService
          .find(new Expansion("body.storage"))
          .withSpace(spaces.toArray(new Space[spaces.size()]))
          .fetchManyCompletionStage(ContentType.PAGE, pageRequest)
          .toCompletableFuture()
          .get();

    } catch (Exception e) {
      return PageResponseImpl.empty(true, pageRequest);
    }
  }

  @Override
  public void close() throws IOException {
    provider.close();
    LOGGER.info("Confluence client has been closed");
  }
}
