package com.koval.jresolver.connector.confluence;

import com.atlassian.confluence.api.model.Expansion;
import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.ContentRepresentation;
import com.atlassian.confluence.api.model.content.ContentType;
import com.atlassian.confluence.api.model.content.Space;
import com.atlassian.confluence.api.model.pagination.PageRequest;
import com.atlassian.confluence.api.model.pagination.PageResponse;
import com.atlassian.confluence.api.model.pagination.SimplePageRequest;
import com.atlassian.confluence.rest.client.RemoteContentService;
import com.atlassian.confluence.rest.client.RemoteContentServiceImpl;
import com.atlassian.confluence.rest.client.RemoteSpaceService;
import com.atlassian.confluence.rest.client.RemoteSpaceServiceImpl;
import com.atlassian.confluence.rest.client.RestClientFactory;
import com.atlassian.confluence.rest.client.authentication.AuthenticatedWebResourceProvider;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.koval.jresolver.connector.confluence.configuration.ConfluenceConnectorProperties;
import com.sun.jersey.api.client.Client;


@SuppressWarnings("PMD")
public final class ConfluenceConnector {

  private final ConfluenceConnectorProperties connectorProperties;

  private ConfluenceConnector(ConfluenceConnectorProperties connectorProperties) {
    this.connectorProperties = connectorProperties;
  }

  public void main(String[] args) {
    Client client = RestClientFactory.newClient();
    ListeningExecutorService executor = MoreExecutors.newDirectExecutorService();

    try (AuthenticatedWebResourceProvider provider =
             new AuthenticatedWebResourceProvider(client, connectorProperties.getConfluenceBaseUrl(), "")) {

      if (!connectorProperties.isAnonymous()) {
        provider.setAuthContext(connectorProperties.getUsername(), connectorProperties.getPassword().toCharArray());
      }

      RemoteSpaceService spaceService = new RemoteSpaceServiceImpl(provider, executor);
      Space space = spaceService.find().withKeys(connectorProperties.getSpaceKey()).fetchOne().claim().get();
      printSpaceInfo(space);

      RemoteContentService contentService = new RemoteContentServiceImpl(provider, executor);
      printPagesBySpace(contentService, space);
    }
  }

  public static void printSpaceInfo(Space space) {
    System.out.println(space.getName());
    System.out.println(space.getDescription());
    System.out.println(space.getType());
    System.out.println(space.getHomepageRef());
  }

  public void printPagesBySpace(RemoteContentService contentService, Space space) {
    int startIndex = 0;
    int endIndex = connectorProperties.getBatchSize();
    while (true) {
      PageRequest pageRequest = new SimplePageRequest(startIndex, endIndex);
      System.out.println("Create page request: " + pageRequest);
      PageResponse<Content> contentResponse = contentService
          .find(
              // new Expansion("body.storage"),
              // new Expansion("body.view"),
              new Expansion("body.editor")
          )
          .withSpace(space)
          .fetchMany(ContentType.PAGE, pageRequest)
          .claim();
      System.out.println("Fetched confluence pages: " + contentResponse.size());

      contentResponse.getResults().forEach((Content result) -> {
        System.out.println("Page ID: " + result.getId().serialise());
        System.out.println("Page Title: " + result.getTitle());
        System.out.println("Page Body: " + result.getBody().get(ContentRepresentation.EDITOR).getValue()
            .replace('\n', ' ')
            .replaceAll("\\<.*?\\>", ""));
        System.out.println();
        System.out.println();
      });

      startIndex += contentResponse.size();
      endIndex += contentResponse.size();
      if (!contentResponse.hasMore()) {
        break;
      }
    }
  }
}
