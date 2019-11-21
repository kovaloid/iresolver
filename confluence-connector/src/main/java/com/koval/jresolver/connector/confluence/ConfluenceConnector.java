package com.koval.jresolver.connector.confluence;

import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.Space;
import com.atlassian.confluence.rest.client.RemoteContentService;
import com.atlassian.confluence.rest.client.RemoteContentServiceImpl;
import com.atlassian.confluence.rest.client.RemoteSpaceService;
import com.atlassian.confluence.rest.client.RemoteSpaceServiceImpl;
import com.atlassian.confluence.rest.client.RestClientFactory;
import com.atlassian.confluence.rest.client.authentication.AuthenticatedWebResourceProvider;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.sun.jersey.api.client.Client;


public class ConfluenceConnector {

  public static void main(String[] args) {
    Client client = RestClientFactory.newClient();
    AuthenticatedWebResourceProvider authProvider =
        new AuthenticatedWebResourceProvider(client, "<confluence url>", "");
    authProvider.setAuthContext("<login>", "<password>".toCharArray());
    ListeningExecutorService executor = MoreExecutors.newDirectExecutorService();
    RemoteSpaceService spaceService = new RemoteSpaceServiceImpl(authProvider, executor);
    Space space = spaceService.find().withKeys("<space key>").fetchOne().claim().get();

    System.out.println(space.getName());
    System.out.println(space.getDescription());
    System.out.println(space.getType());
    System.out.println(space.getHomepageRef());

    RemoteContentService contentService = new RemoteContentServiceImpl(authProvider, executor);
    Content content = contentService.find().fetchOne().claim().get();

    System.out.println(content.getBody());
  }
}
