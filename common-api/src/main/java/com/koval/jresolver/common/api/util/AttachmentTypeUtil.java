package com.koval.jresolver.common.api.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.koval.jresolver.common.api.bean.issue.Attachment;
import com.koval.jresolver.common.api.bean.result.AttachmentType;


public final class AttachmentTypeUtil {

  private AttachmentTypeUtil() {
  }

  @SuppressWarnings({"PMD.CyclomaticComplexity"})
  public static AttachmentType getType(Attachment attachment) {
    // TODO: implement using mime type
    String fileName = attachment.getFileName();
    if (fileName.endsWith(".txt") || fileName.endsWith(".log")) {
      return AttachmentType.LOG;
    } else if (fileName.endsWith(".pdf") || fileName.endsWith(".doc") || fileName.endsWith(".docx")) {
      return AttachmentType.DOCUMENT;
    } else if (fileName.endsWith(".csv")) {
      return AttachmentType.TABLE;
    } else if (fileName.endsWith(".zip")) {
      return AttachmentType.ARCHIVE;
    } else if (fileName.endsWith(".png")) {
      return AttachmentType.PICTURE;
    } else if (fileName.endsWith(".arf")) {
      return AttachmentType.VIDEO;
    } else {
      return AttachmentType.UNKNOWN;
    }
  }

  public static List<AttachmentType> getTypes(List<Attachment> attachments) {
    Set<AttachmentType> types = new HashSet<>();
    for (Attachment attachment: attachments) {
      types.add(getType(attachment));
    }
    return new ArrayList<>(types);
  }
}
