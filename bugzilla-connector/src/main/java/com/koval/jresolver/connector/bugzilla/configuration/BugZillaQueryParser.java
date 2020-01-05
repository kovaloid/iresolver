package com.koval.jresolver.connector.bugzilla.configuration;


public class BugZillaQueryParser {

  @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.StdCyclomaticComplexity", "PMD.ModifiedCyclomaticComplexity"})
  public BugZillaQuery parse(String rawQuery) {
    BugZillaQuery query = new BugZillaQuery();
    String[] expressions = rawQuery.split(",");
    for (String expression: expressions) {
      String[] parts = expression.split("=");
      String name = parts[0].trim();
      String value = parts[1].trim();
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
