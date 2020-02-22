package com.koval.resolver.connector.jira.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.BasicUser;
import com.koval.resolver.common.api.bean.issue.Attachment;
import com.koval.resolver.common.api.bean.issue.Comment;
import com.koval.resolver.common.api.bean.issue.Component;
import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.issue.IssueField;
import com.koval.resolver.common.api.bean.issue.IssueLink;
import com.koval.resolver.common.api.bean.issue.IssueType;
import com.koval.resolver.common.api.bean.issue.Project;
import com.koval.resolver.common.api.bean.issue.SubTask;
import com.koval.resolver.common.api.bean.issue.User;
import com.koval.resolver.common.api.bean.issue.Version;
import com.koval.resolver.common.api.component.connector.IssueTransformer;
import com.koval.resolver.common.api.util.CollectionsUtil;


public class JiraIssueTransformer implements IssueTransformer<com.atlassian.jira.rest.client.api.domain.Issue> {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraIssueTransformer.class);
  private static final Map<String, User> USER_CACHE = new HashMap<>();
  private static final String UNKNOWN = "<unknown>";

  private final JiraRestClient restClient;
  private final String browseUrl;

  public JiraIssueTransformer(JiraRestClient restClient, String browseUrl) {
    this.restClient = restClient;
    this.browseUrl = browseUrl;
  }

  @Override
  public Issue transform(com.atlassian.jira.rest.client.api.domain.Issue originalIssue) {
    Issue transformedIssue = new Issue();

    transformedIssue.setLink(URI.create(browseUrl + originalIssue.getKey()));
    transformedIssue.setKey(originalIssue.getKey());
    transformedIssue.setSummary(originalIssue.getSummary());
    transformedIssue.setDescription(originalIssue.getDescription());
    transformedIssue.setStatus(originalIssue.getStatus().getName());
    if (originalIssue.getResolution() == null) {
      transformedIssue.setResolution("");
    } else {
      transformedIssue.setResolution(originalIssue.getResolution().getName());
    }
    if (originalIssue.getPriority() == null) {
      transformedIssue.setPriority("");
    } else {
      transformedIssue.setPriority(originalIssue.getPriority().getName());
    }

    if (originalIssue.getReporter() == null) {
      transformedIssue.setReporter(getUnknownUser());
    } else {
      transformedIssue.setReporter(transformUser(originalIssue.getReporter()));
    }
    if (originalIssue.getAssignee() == null) {
      transformedIssue.setAssignee(getUnknownUser());
    } else {
      transformedIssue.setAssignee(transformUser(originalIssue.getAssignee()));
    }

    transformedIssue.setIssueType(new IssueType(originalIssue.getIssueType().getName(),
        originalIssue.getIssueType().isSubtask()));
    transformedIssue.setProject(new Project(originalIssue.getProject().getKey(),
        originalIssue.getProject().getName()));
    transformedIssue.setCreationDate(originalIssue.getCreationDate());
    transformedIssue.setUpdateDate(originalIssue.getUpdateDate());
    transformedIssue.setDueDate(originalIssue.getDueDate());

    transformedIssue.setLabels(new ArrayList<>(originalIssue.getLabels()));
    transformedIssue.setComponents(transformComponents(originalIssue));
    transformedIssue.setFixVersions(transformFixVersions(originalIssue));
    transformedIssue.setAffectedVersions(transformAffectedVersions(originalIssue));
    transformedIssue.setComments(transformComments(originalIssue));
    transformedIssue.setIssueLinks(transformIssueLinks(originalIssue));
    transformedIssue.setAttachments(transformAttachments(originalIssue));
    transformedIssue.setSubTasks(transformSubTasks(originalIssue));
    transformedIssue.setIssueFields(transformIssueFields(originalIssue));

    return transformedIssue;
  }

  private List<Component> transformComponents(com.atlassian.jira.rest.client.api.domain.Issue originalIssue) {
    List<Component> transformedComponents = new ArrayList<>();
    if (originalIssue.getComponents() != null) {
      originalIssue.getComponents().forEach(originalComponent -> {
        Component transformedComponent = new Component(originalComponent.getName(), originalComponent.getDescription());
        transformedComponents.add(transformedComponent);
      });
    }
    return transformedComponents;
  }

  private List<Version> transformFixVersions(com.atlassian.jira.rest.client.api.domain.Issue originalIssue) {
    List<Version> transformedFixVersions = new ArrayList<>();
    if (originalIssue.getFixVersions() != null) {
      originalIssue.getFixVersions().forEach(originalFixVersion -> {
        transformedFixVersions.add(transformVersion(originalFixVersion));
      });
    }
    return transformedFixVersions;
  }

  private List<Version> transformAffectedVersions(com.atlassian.jira.rest.client.api.domain.Issue originalIssue) {
    List<Version> transformedAffectedVersions = new ArrayList<>();
    if (originalIssue.getAffectedVersions() != null) {
      originalIssue.getAffectedVersions().forEach(originalAffectedVersion -> {
        transformedAffectedVersions.add(transformVersion(originalAffectedVersion));
      });
    }
    return transformedAffectedVersions;
  }

  private List<Comment> transformComments(com.atlassian.jira.rest.client.api.domain.Issue originalIssue) {
    List<Comment> transformedComments = new ArrayList<>();
    if (originalIssue.getComments() != null) {
      originalIssue.getComments().forEach(originalComment -> {
        Comment transformedComment = new Comment(transformBasicUser(originalComment.getAuthor()),
            transformBasicUser(originalComment.getUpdateAuthor()), originalComment.getCreationDate(),
            originalComment.getUpdateDate(), originalComment.getBody());
        transformedComments.add(transformedComment);
      });
    }
    return transformedComments;
  }

  private List<IssueLink> transformIssueLinks(com.atlassian.jira.rest.client.api.domain.Issue originalIssue) {
    List<IssueLink> transformedIssueLinks = new ArrayList<>();
    if (originalIssue.getIssueLinks() != null) {
      originalIssue.getIssueLinks().forEach(originalIssueLink -> {
        IssueLink transformedIssueLink = new IssueLink(originalIssueLink.getTargetIssueKey(),
            originalIssueLink.getIssueLinkType().getName(), originalIssueLink.getTargetIssueUri());
        transformedIssueLinks.add(transformedIssueLink);
      });
    }
    return transformedIssueLinks;
  }

  private List<Attachment> transformAttachments(com.atlassian.jira.rest.client.api.domain.Issue originalIssue) {
    List<Attachment> transformedAttachments = new ArrayList<>();
    if (originalIssue.getAttachments() != null) {
      originalIssue.getAttachments().forEach(originalAttachment -> {
        Attachment transformedAttachment = new Attachment(originalAttachment.getFilename(),
            transformBasicUser(originalAttachment.getAuthor()), originalAttachment.getCreationDate(),
            originalAttachment.getSize(), originalAttachment.getMimeType(), originalAttachment.getContentUri());
        transformedAttachments.add(transformedAttachment);
      });
    }
    return transformedAttachments;
  }

  private List<SubTask> transformSubTasks(com.atlassian.jira.rest.client.api.domain.Issue originalIssue) {
    List<SubTask> transformedSubTasks = new ArrayList<>();
    if (originalIssue.getSubtasks() != null) {
      originalIssue.getSubtasks().forEach(originalSubTask -> {
        IssueType subTaskIssueType = new IssueType(originalSubTask.getIssueType().getName(),
            originalSubTask.getIssueType().isSubtask());
        SubTask transformedSubTask = new SubTask(originalSubTask.getIssueKey(), originalSubTask.getIssueUri(),
            originalSubTask.getSummary(), subTaskIssueType, originalSubTask.getStatus().getName());
        transformedSubTasks.add(transformedSubTask);
      });
    }
    return transformedSubTasks;
  }

  private List<IssueField> transformIssueFields(com.atlassian.jira.rest.client.api.domain.Issue originalIssue) {
    List<IssueField> transformedIssueFields = new ArrayList<>();
    if (originalIssue.getFields() != null) {
      originalIssue.getFields().forEach(originalIssueField -> {
        IssueField transformedIssueField = new IssueField(originalIssueField.getId(), originalIssueField.getName(),
            originalIssueField.getType(), originalIssueField.getValue());
        transformedIssueFields.add(transformedIssueField);
      });
    }
    return transformedIssueFields;
  }

  private User transformUser(com.atlassian.jira.rest.client.api.domain.User originalUser) {
    User transformedUser = new User();
    transformedUser.setName(originalUser.getName());
    transformedUser.setDisplayName(originalUser.getDisplayName());
    transformedUser.setEmailAddress(originalUser.getEmailAddress());
    transformedUser.setAvatarUri(originalUser.getAvatarUri());
    transformedUser.setSmallAvatarUri(originalUser.getSmallAvatarUri());
    if (originalUser.getGroups() == null) {
      transformedUser.setGroups(new ArrayList<>());
    } else {
      transformedUser.setGroups(CollectionsUtil.convert(originalUser.getGroups().getItems()));
    }
    return transformedUser;
  }

  private User transformBasicUser(BasicUser originalBasicUser) {
    if (originalBasicUser == null) {
      return getUnknownUser();
    }
    if (USER_CACHE.containsKey(originalBasicUser.getName())) {
      return USER_CACHE.get(originalBasicUser.getName());
    }
    try {
      User user = getFullUserByBasicUser(originalBasicUser);
      USER_CACHE.put(originalBasicUser.getName(), user);
      return user;
    } catch (RestClientException e) {
      LOGGER.debug("Could not get full data about user {}", e.getErrorCollections());
      User user = getIncompleteUserByBasicUser(originalBasicUser);
      USER_CACHE.put(originalBasicUser.getName(), user);
      return user;
    }
  }

  private User getUnknownUser() {
    return new User(UNKNOWN, UNKNOWN, UNKNOWN, new ArrayList<>(),
        URI.create(""), URI.create(""));
  }

  private User getFullUserByBasicUser(BasicUser basicUser) {
    com.atlassian.jira.rest.client.api.domain.User fullUser = restClient.getUserClient()
        .getUser(basicUser.getSelf()).claim();
    List<String> userGroups = fullUser.getGroups() == null ? new ArrayList<>()
        : CollectionsUtil.convert(fullUser.getGroups().getItems());
    return new User(basicUser.getName(), basicUser.getDisplayName(), fullUser.getEmailAddress(),
        userGroups, fullUser.getAvatarUri(), fullUser.getSmallAvatarUri());
  }

  private User getIncompleteUserByBasicUser(BasicUser basicUser) {
    return new User(basicUser.getName(), basicUser.getDisplayName(), UNKNOWN, new ArrayList<>(), URI.create(""),
        URI.create(""));
  }

  private Version transformVersion(com.atlassian.jira.rest.client.api.domain.Version originalVersion) {
    return new Version(originalVersion.getName(), originalVersion.getDescription(), originalVersion.isArchived(),
        originalVersion.isReleased(), originalVersion.getReleaseDate());
  }

  @Override
  public List<Issue> transform(Collection<com.atlassian.jira.rest.client.api.domain.Issue> originalIssues) {
    List<Issue> transformedIssues = new ArrayList<>();
    for (com.atlassian.jira.rest.client.api.domain.Issue originalIssue : originalIssues) {
      transformedIssues.add(transform(originalIssue));
    }
    return transformedIssues;
  }
}
