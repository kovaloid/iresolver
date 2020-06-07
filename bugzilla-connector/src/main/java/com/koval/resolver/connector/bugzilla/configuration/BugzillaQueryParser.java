package com.koval.resolver.connector.bugzilla.configuration;


public class BugzillaQueryParser {

  @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.StdCyclomaticComplexity", "PMD.ModifiedCyclomaticComplexity"})
  public BugzillaQuery parse(final String rawQuery) {
    final BugzillaQuery query = new BugzillaQuery();
    final String[] expressions = rawQuery.split(",");
    for (final String expression: expressions) {
      final String[] parts = expression.split("=");
      final String name = parts[0].trim();
      final String value = parts[1].trim();
      if ("product".equalsIgnoreCase(name)) {
        query.setProduct(value);
      } else if ("status".equalsIgnoreCase(name)) {
        query.setStatus(value);
      } else if ("resolution".equalsIgnoreCase(name)) {
        query.setResolution(value);
      } else if ("assignee".equalsIgnoreCase(name)) {
        query.setAssignee(value);
      } else if ("reporter".equalsIgnoreCase(name)) {
        query.setReporter(value);
      } else if ("version".equalsIgnoreCase(name)) {
        query.setVersion(value);
      } else if ("priority".equalsIgnoreCase(name)) {
        query.setPriority(value);
      }
    }
    return query;
  }
}
