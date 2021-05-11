package com.koval.resolver.connector.gitlab.configuration;


public class GitlabQueryParser {

  @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.StdCyclomaticComplexity", "PMD.ModifiedCyclomaticComplexity"})
  public GitlabQuery parse(final String rawQuery) {
    final GitlabQuery query = new GitlabQuery();
    final String[] expressions = rawQuery.split(",");
    for (final String expression : expressions) {
      final String[] parts = expression.split("=");
      final String name = parts[0].trim();
      final String value = parts[1].trim();
      if ("project".equalsIgnoreCase(name)) {
        query.setProject(value);
      } else if ("status".equalsIgnoreCase(name)) {
        query.setStatus(value);
      } else if ("label".equalsIgnoreCase(name)) {
        query.setLabel(value);
      } else if ("assigneeId".equalsIgnoreCase(name)) {
        query.setAssigneeId(Integer.valueOf(value));
      } else if ("milestone".equalsIgnoreCase(name)) {
        query.setMilestone(value);
      } else if ("authorId".equalsIgnoreCase(name)) {
        query.setAuthorId(Integer.valueOf(value));
      }
    }
    return query;
  }
}
