package com.koval.resolver.connector.bugzilla.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.koval.resolver.common.api.bean.issue.Attachment;
import com.koval.resolver.common.api.bean.issue.Comment;
import com.koval.resolver.common.api.bean.issue.Component;
import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.issue.IssueLink;
import com.koval.resolver.common.api.bean.issue.IssueType;
import com.koval.resolver.common.api.bean.issue.Project;
import com.koval.resolver.common.api.bean.issue.SubTask;
import com.koval.resolver.common.api.bean.issue.User;
import com.koval.resolver.common.api.bean.issue.Version;
import com.koval.resolver.common.api.component.connector.IssueTransformer;


public class BugzillaIssueTransformer implements IssueTransformer<b4j.core.Issue> {

  private static final Map<String, User> USER_CACHE = new HashMap<>();
  private static final String UNKNOWN = "<unknown>";

  @Override
  public Issue transform(b4j.core.Issue originalIssue) {
    Issue transformedIssue = new Issue();

    transformedIssue.setLink(URI.create(originalIssue.getUri()));
    transformedIssue.setKey(originalIssue.getId());
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

    transformedIssue.setIssueType(new IssueType(originalIssue.getType().getName(), false));
    transformedIssue.setProject(new Project(originalIssue.getProject().getId(),
        originalIssue.getProject().getName()));
    transformedIssue.setCreationDate(new DateTime(originalIssue.getCreationTimestamp()));
    transformedIssue.setUpdateDate(new DateTime(originalIssue.getUpdateTimestamp()));
    transformedIssue.setDueDate(null);

    transformedIssue.setLabels(new ArrayList<>());
    transformedIssue.setComponents(transformComponents(originalIssue));
    transformedIssue.setFixVersions(transformFixVersions(originalIssue));
    transformedIssue.setAffectedVersions(transformAffectedVersions(originalIssue));
    transformedIssue.setComments(transformComments(originalIssue));
    transformedIssue.setIssueLinks(transformIssueLinks(originalIssue));
    transformedIssue.setAttachments(transformAttachments(originalIssue));
    transformedIssue.setSubTasks(transformSubTasks(originalIssue));
    transformedIssue.setIssueFields(new ArrayList<>());

    return transformedIssue;
  }

  private List<Component> transformComponents(b4j.core.Issue originalIssue) {
    List<Component> transformedComponents = new ArrayList<>();
    if (originalIssue.getComponents() != null) {
      originalIssue.getComponents().forEach(originalComponent -> {
        Component transformedComponent = new Component(originalComponent.getName(), originalComponent.getDescription());
        transformedComponents.add(transformedComponent);
      });
    }
    return transformedComponents;
  }

  private List<Version> transformFixVersions(b4j.core.Issue originalIssue) {
    List<Version> transformedFixVersions = new ArrayList<>();
    if (originalIssue.getFixVersions() != null) {
      originalIssue.getFixVersions().forEach(originalFixVersion -> {
        transformedFixVersions.add(transformVersion(originalFixVersion));
      });
    }
    return transformedFixVersions;
  }

  private List<Version> transformAffectedVersions(b4j.core.Issue originalIssue) {
    List<Version> transformedAffectedVersions = new ArrayList<>();
    if (originalIssue.getAffectedVersions() != null) {
      originalIssue.getAffectedVersions().forEach(originalAffectedVersion -> {
        transformedAffectedVersions.add(transformVersion(originalAffectedVersion));
      });
    }
    return transformedAffectedVersions;
  }

  private List<Comment> transformComments(b4j.core.Issue originalIssue) {
    List<Comment> transformedComments = new ArrayList<>();
    if (originalIssue.getComments() != null) {
      originalIssue.getComments().forEach(originalComment -> {
        Comment transformedComment = new Comment(transformUser(originalComment.getAuthor()),
            transformUser(originalComment.getUpdateAuthor()), new DateTime(originalComment.getCreationTimestamp()),
            new DateTime(originalComment.getUpdateTimestamp()), originalComment.getTheText());
        transformedComments.add(transformedComment);
      });
    }
    return transformedComments;
  }

  private List<IssueLink> transformIssueLinks(b4j.core.Issue originalIssue) {
    List<IssueLink> transformedIssueLinks = new ArrayList<>();
    if (originalIssue.getLinks() != null) {
      originalIssue.getLinks().forEach(originalIssueLink -> {
        IssueLink transformedIssueLink = new IssueLink(originalIssueLink.getIssueId(),
            originalIssueLink.getLinkTypeName(), null);
        transformedIssueLinks.add(transformedIssueLink);
      });
    }
    return transformedIssueLinks;
  }

  private List<Attachment> transformAttachments(b4j.core.Issue originalIssue) {
    List<Attachment> transformedAttachments = new ArrayList<>();
    if (originalIssue.getAttachments() != null) {
      originalIssue.getAttachments().forEach(originalAttachment -> {
        Attachment transformedAttachment = new Attachment(originalAttachment.getFilename(), null, null, 0,
            originalAttachment.getDescription(), originalAttachment.getUri());
        transformedAttachments.add(transformedAttachment);
      });
    }
    return transformedAttachments;
  }

  private List<SubTask> transformSubTasks(b4j.core.Issue originalIssue) {
    List<SubTask> transformedSubTasks = new ArrayList<>();
    if (originalIssue.getChildren() != null) {
      originalIssue.getChildren().forEach(originalSubTask -> {
        IssueType subTaskIssueType = new IssueType(originalSubTask.getType().getName(),
            true);
        SubTask transformedSubTask = new SubTask(originalSubTask.getId(), URI.create(originalSubTask.getUri()),
            originalSubTask.getSummary(), subTaskIssueType, originalSubTask.getStatus().getName());
        transformedSubTasks.add(transformedSubTask);
      });
    }
    return transformedSubTasks;
  }

  private User transformUser(b4j.core.User originalUser) {
    if (originalUser == null) {
      return getUnknownUser();
    }
    if (USER_CACHE.containsKey(originalUser.getName())) {
      return USER_CACHE.get(originalUser.getName());
    }
    User transformedUser = new User(
        originalUser.getName(),
        originalUser.getRealName(),
        UNKNOWN,
        originalUser.getTeam() == null ? new ArrayList<>()
            : Collections.singletonList(originalUser.getTeam().getName()),
        URI.create(""),
        URI.create("")
    );
    USER_CACHE.put(originalUser.getName(), transformedUser);
    return transformedUser;
  }

  private User getUnknownUser() {
    return new User(UNKNOWN, UNKNOWN, UNKNOWN, new ArrayList<>(), URI.create(""), URI.create(""));
  }

  private Version transformVersion(b4j.core.Version originalVersion) {
    Date releaseDate = originalVersion.getReleaseDate();
    if (releaseDate == null) {
      return new Version(originalVersion.getName(), "", false, false, null);
    }
    return new Version(originalVersion.getName(), "", false,
        originalVersion.getReleaseDate().before(new Date()), new DateTime(originalVersion.getReleaseDate()));
  }

  @Override
  public List<Issue> transform(Collection<b4j.core.Issue> originalIssues) {
    List<Issue> transformedIssues = new ArrayList<>();
    for (b4j.core.Issue originalIssue : originalIssues) {
      transformedIssues.add(transform(originalIssue));
    }
    return transformedIssues;
  }
}
