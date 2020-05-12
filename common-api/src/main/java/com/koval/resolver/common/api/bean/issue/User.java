package com.koval.resolver.common.api.bean.issue;

import java.net.URI;
import java.util.List;
import java.util.Objects;

public class User {

  private String name;
  private String displayName;
  private String emailAddress;
  private List<String> groups;
  private URI avatarUri;
  private URI smallAvatarUri;

  public User() {
  }

  public User(
    final String name, final String displayName, final String emailAddress, final List<String> groups,
    final URI avatarUri, final URI smallAvatarUri) {
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

  public void setName(final String name) {
    this.name = name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(final String displayName) {
    this.displayName = displayName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(final String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public List<String> getGroups() {
    return groups;
  }

  public void setGroups(final List<String> groups) {
    this.groups = groups;
  }

  public URI getAvatarUri() {
    return avatarUri;
  }

  public void setAvatarUri(final URI avatarUri) {
    this.avatarUri = avatarUri;
  }

  public URI getSmallAvatarUri() {
    return smallAvatarUri;
  }

  public void setSmallAvatarUri(final URI smallAvatarUri) {
    this.smallAvatarUri = smallAvatarUri;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, displayName, emailAddress, groups, avatarUri, smallAvatarUri);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final User user = (User)o;
    return Objects.equals(name, user.name)
           && Objects.equals(displayName, user.displayName)
           && Objects.equals(emailAddress, user.emailAddress)
           && Objects.equals(groups, user.groups)
           && Objects.equals(avatarUri, user.avatarUri)
           && Objects.equals(smallAvatarUri, user.smallAvatarUri);
  }

}
