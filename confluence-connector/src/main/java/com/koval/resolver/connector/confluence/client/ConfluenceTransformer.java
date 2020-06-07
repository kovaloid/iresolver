package com.koval.resolver.connector.confluence.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.ContentBody;
import com.atlassian.confluence.api.model.content.ContentRepresentation;
import com.koval.resolver.common.api.bean.confluence.ConfluencePage;


public class ConfluenceTransformer {

  public ConfluencePage transform(final Content content) {
    final ConfluencePage page = new ConfluencePage();
    page.setId(content.getId().asLong());
    page.setTitle(content.getTitle());
    final ContentBody contentBody = content.getBody()
        .get(ContentRepresentation.STORAGE);
    final String bodyWithoutTags = contentBody.getValue()
        .replaceAll("nbsp", "")
        .replaceAll("<.*?>", "");
    page.setBody(bodyWithoutTags);
    return page;
  }

  public List<ConfluencePage> transform(final Collection<Content> contents) {
    final List<ConfluencePage> pages = new ArrayList<>();
    for (final Content content : contents) {
      pages.add(transform(content));
    }
    return pages;
  }
}
