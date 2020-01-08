package com.koval.jresolver.common.api.bean.result;


public class ConfluenceResult {

  private int pageId;
  private String title;
  private String browseUrl;

  public ConfluenceResult(int pageId, String title, String browseUrl) {
    this.pageId = pageId;
    this.title = title;
    this.browseUrl = browseUrl;
  }

  public int getPageId() {
    return pageId;
  }

  public void setPageId(int pageId) {
    this.pageId = pageId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBrowseUrl() {
    return browseUrl;
  }

  public void setBrowseUrl(String browseUrl) {
    this.browseUrl = browseUrl;
  }
}
