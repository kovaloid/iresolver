package com.koval.jresolver.processor.confluence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.result.ConfluenceResult;
import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.jresolver.common.api.component.processor.IssueProcessor;
import com.koval.jresolver.common.api.configuration.Configuration;
import com.koval.jresolver.common.api.doc2vec.TextDataExtractor;
import com.koval.jresolver.common.api.doc2vec.VectorModel;
import com.koval.jresolver.common.api.doc2vec.VectorModelSerializer;


public class ConfluenceProcessor implements IssueProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfluenceProcessor.class);
  private static final int NUMBER_OF_NEAREST_LABELS = 10;
  private static final String BROWSE_SUFFIX = "/display/pages/viewinfo.action?pageId=";

  private final VectorModel vectorModel;
  private final TextDataExtractor textDataExtractor = new TextDataExtractor();
  private final String confluenceUrl;

  public ConfluenceProcessor(Configuration properties) throws IOException {
    this.confluenceUrl = properties.getConnectors().getConfluence().getUrl();
    VectorModelSerializer vectorModelSerializer = new VectorModelSerializer();
    File vectorModelFile = new File(properties.getProcessors().getConfluence().getVectorModelFile());
    this.vectorModel = vectorModelSerializer.deserialize(vectorModelFile, properties.getParagraphVectors().getLanguage());
  }

  @Override
  public void run(Issue issue, IssueAnalysingResult result) {
    setOriginalIssueToResults(issue, result);
    Collection<String> similarPageKeys = vectorModel.getNearestLabels(textDataExtractor.extract(issue),
        NUMBER_OF_NEAREST_LABELS);
    LOGGER.info("Nearest confluence keys for {}: {}", issue.getKey(), similarPageKeys);

    List<ConfluenceResult> confluenceResults = new ArrayList<>();
    similarPageKeys.forEach(key -> {
      ConfluenceResult confluenceResult = new ConfluenceResult(Integer.parseInt(key), "", getBrowseUrl(key));
      confluenceResults.add(confluenceResult);
    });
    result.setConfluenceResults(confluenceResults);
  }

  private String getBrowseUrl(String pageId) {
    return confluenceUrl + BROWSE_SUFFIX + pageId;
  }
}
