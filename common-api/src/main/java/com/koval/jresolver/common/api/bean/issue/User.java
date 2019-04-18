package com.koval.jresolver.common.api.bean.issue;

import java.net.URI;
import java.util.List;


public class User {

  private String name;
  private String displayName;
  private String emailAddress;
  private List<String> groups;
  private URI avatarUri;
  private URI smallAvatarUri;

  public User() {
  }

  public User(String name, String displayName, String emailAddress, List<String> groups, URI avatarUri, URI smallAvatarUri) {
    this.name = name;
    this.displayName = displayName;
    this.emailAddress = emailAddress;
    this.groups = groups;
    this.avatarUri = avatarUri;
    this.smallAvatarUri = smallAvatarUri;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public List<String> getGroups() {
    return groups;
  }

  public void setGroups(List<String> groups) {
    this.groups = groups;
  }

  public URI getAvatarUri() {
    return avatarUri;
  }

  public void setAvatarUri(URI avatarUri) {
    this.avatarUri = avatarUri;
  }

  public URI getSmallAvatarUri() {
    return smallAvatarUri;
  }

  public void setSmallAvatarUri(URI smallAvatarUri) {
    this.smallAvatarUri = smallAvatarUri;
  }
}
