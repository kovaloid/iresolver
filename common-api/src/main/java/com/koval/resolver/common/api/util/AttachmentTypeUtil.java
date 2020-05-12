package com.koval.resolver.common.api.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.koval.resolver.common.api.bean.issue.Attachment;


public final class AttachmentTypeUtil {

  private AttachmentTypeUtil() {
  }

  @SuppressWarnings({"PMD.CyclomaticComplexity"})
  public static String getType(final String extension) {
    if (extension.endsWith(".txt") || extension.endsWith(".log")) {
      return "Log";
    } else if (extension.endsWith(".pdf") || extension.endsWith(".doc") || extension.endsWith(".docx") || extension.endsWith(".rtf")) {
      return "Document";
    } else if (extension.endsWith(".xml") || extension.endsWith(".json") || extension.endsWith(".yaml")) {
      return "Configuration";
    } else if (extension.endsWith(".csv") || extension.endsWith(".xls")) {
      return "Table";
    } else if (extension.endsWith(".zip") || extension.endsWith(".tar")) {
      return "Archive";
    } else if (extension.endsWith(".png") || extension.endsWith(".jpg") || extension.endsWith(".jpeg")
        || extension.endsWith(".gif") || extension.endsWith(".bmp")) {
      return "Picture";
    } else if (extension.endsWith(".arf")) {
      return "Video";
    } else {
      return "Unknown";
    }
  }

  public static String getExtension(final Attachment attachment) {
    final String fileName = attachment.getFileName();
    final int dotIndex = fileName.lastIndexOf('.');
    return dotIndex > 0 ? fileName.substring(dotIndex) : "";
  }

  public static List<String> getExtensions(final List<Attachment> attachments) {
    final Set<String> types = new HashSet<>();
    for (final Attachment attachment: attachments) {
      types.add(getExtension(attachment));
    }
    return new ArrayList<>(types);
  }
}
