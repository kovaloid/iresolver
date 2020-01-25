package com.koval.jresolver.connector.confluence.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.ContentBody;
import com.atlassian.confluence.api.model.content.ContentRepresentation;
import com.koval.jresolver.common.api.bean.confluence.ConfluencePage;


public class ConfluenceTransformer {

  public ConfluencePage transform(Content content) {
    ConfluencePage page = new ConfluencePage();
    page.setId(content.getId().asLong());
    page.setTitle(content.getTitle());
    ContentBody contentBody = content.getBody()
        .get(ContentRepresentation.STORAGE);
    String bodyWithoutTags = contentBody.getValue()
        .replaceAll("nbsp", "")
        .replaceAll("<.*?>", "");
    page.setBody(bodyWithoutTags);
    return page;
  }

  public List<ConfluencePage> transform(Collection<Content> contents) {
    List<ConfluencePage> pages = new ArrayList<>();
    for (Content content : contents) {
      pages.add(transform(content));
    }
    return pages;
  }
}
