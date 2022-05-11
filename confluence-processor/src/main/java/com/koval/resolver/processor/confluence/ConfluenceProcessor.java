package com.koval.resolver.processor.confluence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.component.processor.IssueProcessor;
import com.koval.resolver.common.api.configuration.Configuration;
import com.koval.resolver.common.api.model.issue.Issue;
import com.koval.resolver.common.api.model.result.ConfluenceResult;
import com.koval.resolver.common.api.model.result.IssueAnalysingResult;
import com.koval.resolver.common.api.vectorization.TextDataExtractor;
import com.koval.resolver.common.api.vectorization.VectorModel;
import com.koval.resolver.common.api.vectorization.VectorModelSerializer;


public class ConfluenceProcessor implements IssueProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfluenceProcessor.class);
  private static final String BROWSE_SUFFIX = "/display/pages/viewinfo.action?pageId=";

  private final VectorModel vectorModel;
  private final TextDataExtractor textDataExtractor = new TextDataExtractor();
  private final String confluenceUrl;

  public ConfluenceProcessor(final Configuration properties) throws IOException {
    this.confluenceUrl = properties.getConnectors().getConfluence().getUrl();
    final VectorModelSerializer vectorModelSerializer = new VectorModelSerializer(properties.getVectorizer());
    final File vectorModelFile = new File(properties.getProcessors().getConfluence().getVectorModelFile());
    this.vectorModel = vectorModelSerializer.deserialize(vectorModelFile,
                                                         properties.getVectorizer().getLanguage());
  }

  @Override
  public void run(final Issue issue, final IssueAnalysingResult result) {
    setOriginalIssueToResults(issue, result);
    final Collection<String> similarPageKeys = vectorModel.getNearestLabels(textDataExtractor.extract(issue));
    LOGGER.info("Nearest confluence keys for {}: {}", issue.getKey(), similarPageKeys);

    final List<ConfluenceResult> confluenceResults = new ArrayList<>();
    similarPageKeys.forEach(key -> {
      final ConfluenceResult confluenceResult = new ConfluenceResult(Integer.parseInt(key), "", getBrowseUrl(key));
      confluenceResults.add(confluenceResult);
    });
    result.setConfluenceResults(confluenceResults);
  }

  private String getBrowseUrl(final String pageId) {
    return confluenceUrl + BROWSE_SUFFIX + pageId;
  }

}
