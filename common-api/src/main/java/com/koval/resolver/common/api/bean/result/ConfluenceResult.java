package com.koval.resolver.common.api.bean.result;


public class ConfluenceResult {

  private int pageId;
  private String title;
  private String browseUrl;

  public ConfluenceResult(final int pageId, final String title, final String browseUrl) {
    this.pageId = pageId;
    this.title = title;
    this.browseUrl = browseUrl;
  }

  public int getPageId() {
    return pageId;
  }

  public void setPageId(final int pageId) {
    this.pageId = pageId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getBrowseUrl() {
    return browseUrl;
  }

  public void setBrowseUrl(final String browseUrl) {
    this.browseUrl = browseUrl;
  }
}
