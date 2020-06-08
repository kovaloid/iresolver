package com.koval.resolver.core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.ProcessExecutor;
import com.koval.resolver.common.api.bean.confluence.ConfluencePage;
import com.koval.resolver.common.api.bean.issue.IssueField;
import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.component.connector.Connector;
import com.koval.resolver.common.api.component.connector.IssueClient;
import com.koval.resolver.common.api.component.connector.IssueClientFactory;
import com.koval.resolver.common.api.component.connector.IssueReceiver;
import com.koval.resolver.common.api.component.processor.DataSetWriter;
import com.koval.resolver.common.api.component.processor.IssueProcessor;
import com.koval.resolver.common.api.component.reporter.ReportGenerator;
import com.koval.resolver.common.api.configuration.Configuration;
import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.common.api.constant.ConnectorType;
import com.koval.resolver.common.api.constant.IssueParts;
import com.koval.resolver.common.api.constant.ProcessorConstants;
import com.koval.resolver.common.api.constant.ReporterConstants;
import com.koval.resolver.common.api.doc2vec.TextDataExtractor;
import com.koval.resolver.common.api.doc2vec.VectorModel;
import com.koval.resolver.common.api.doc2vec.VectorModelCreator;
import com.koval.resolver.common.api.doc2vec.VectorModelSerializer;
import com.koval.resolver.common.api.exception.ConfigurationException;
import com.koval.resolver.common.api.exception.ConnectorException;
import com.koval.resolver.connector.bugzilla.BugzillaConnector;
import com.koval.resolver.connector.bugzilla.client.BugzillaIssueClientFactory;
import com.koval.resolver.connector.confluence.ConfluenceConnector;
import com.koval.resolver.connector.jira.JiraConnector;
import com.koval.resolver.connector.jira.client.JiraIssueClientFactory;
import com.koval.resolver.exception.IResolverException;
import com.koval.resolver.processor.confluence.ConfluenceProcessor;
import com.koval.resolver.processor.confluence.core.ConfluenceDataSetWriter;
import com.koval.resolver.processor.documentation.DocumentationProcessor;
import com.koval.resolver.processor.documentation.bean.MediaType;
import com.koval.resolver.processor.documentation.convert.FileConverter;
import com.koval.resolver.processor.documentation.convert.impl.HtmlToPdfFileConverter;
import com.koval.resolver.processor.documentation.convert.impl.PptPptxToPdfFileConverter;
import com.koval.resolver.processor.documentation.convert.impl.WordToPdfFileConverter;
import com.koval.resolver.processor.documentation.convert.impl.XwpfPdfConverter;
import com.koval.resolver.processor.documentation.core.*;
import com.koval.resolver.processor.documentation.split.impl.PdfPageSplitter;
import com.koval.resolver.processor.issues.IssuesProcessor;
import com.koval.resolver.processor.issues.core.IssuesDataSetCreator;
import com.koval.resolver.processor.issues.granular.GranularIssuesProcessor;
import com.koval.resolver.processor.issues.granular.core.GranularIssuesDataSetsCreator;
import com.koval.resolver.processor.issues.test.TestSimilarityProcessor;
import com.koval.resolver.processor.rules.RuleEngineProcessor;
import com.koval.resolver.reporter.html.HtmlReportGenerator;
import com.koval.resolver.reporter.text.TextReportGenerator;

public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private final Configuration configuration;

  public Launcher(final Configuration configuration) {
    this.configuration = configuration;
  }

  public void createIssuesDataSet() {
    try (IssueClient issueClient = getIssueClient()) {
      final Connector connector = getConnector(issueClient);
      String resolvedQuery;
      if (!configuration.getProcessors().getIssues().isOverwriteMode()) {
        String lastSavedIssue = getLastSavedIssue(configuration.getProcessors().getIssues().getDataSetFile());
        if (lastSavedIssue != null) {
          resolvedQuery = configuration.getConnectors().getJira().getResolvedQuery();
          configuration.getConnectors().getJira().setResolvedQuery(resolvedQuery + " AND key > " + lastSavedIssue);
        }
      }
      resolvedQuery = configuration.getConnectors().getJira().getResolvedQuery();
      configuration.getConnectors().getJira().setResolvedQuery(resolvedQuery + " ORDER BY key ASC");
      final IssueReceiver receiver = connector.getResolvedIssuesReceiver();
      final IssuesDataSetCreator dataSetCreator = new IssuesDataSetCreator(receiver,
                                                                           configuration.getProcessors().getIssues());
      dataSetCreator.create();
    } catch (IOException e) {
      LOGGER.error("Could not create data set file.", e);
    }
  }

  public void createIssuesVectorModel() {
    final String dataSetFile = configuration.getProcessors().getIssues().getDataSetFile();
    final String vectorModelFile = configuration.getProcessors().getIssues().getVectorModelFile();
    createVectorModel(dataSetFile, vectorModelFile, "Could not create issues vector model file.");
  }

  public void createGranularIssuesDataSets() {
    try (IssueClient issueClient = getIssueClient()) {
      final Connector connector = getConnector(issueClient);
      final IssueReceiver receiver = connector.getResolvedIssuesReceiver();
      final GranularIssuesDataSetsCreator dataSetCreator = new GranularIssuesDataSetsCreator(receiver,
                                                                                             configuration
                                                                                               .getProcessors()
                                                                                               .getGranularIssues());
      dataSetCreator.create();
    } catch (IOException e) {
      LOGGER.error("Could not create data set files.", e);
    }
  }

  public void createGranularIssuesVectorModels() {
    final List<String> affectedIssueParts = configuration.getProcessors().getGranularIssues().getAffectedIssueParts();
    if (affectedIssueParts.contains(IssueParts.SUMMARY.getContent())) {
      final String dataSetFile = configuration.getProcessors().getGranularIssues().getSummaryDataSetFile();
      final String vectorModelFile = configuration.getProcessors().getGranularIssues().getSummaryVectorModelFile();
      createVectorModel(dataSetFile,
                        vectorModelFile,
                        "Could not create summary granular issues vector model file.");
    }
    if (affectedIssueParts.contains(IssueParts.DESCRIPTION.getContent())) {
      final String dataSetFile = configuration.getProcessors().getGranularIssues().getDescriptionDataSetFile();
      final String vectorModelFile = configuration.getProcessors().getGranularIssues().getDescriptionVectorModelFile();
      createVectorModel(dataSetFile, vectorModelFile,
                        "Could not create description granular issues vector model file.");
    }
    if (affectedIssueParts.contains(IssueParts.COMMENTS.getContent())) {
      final String dataSetFile = configuration.getProcessors().getGranularIssues().getCommentsDataSetFile();
      final String vectorModelFile = configuration.getProcessors().getGranularIssues().getCommentsVectorModelFile();
      createVectorModel(dataSetFile,
                        vectorModelFile,
                        "Could not create comments granular issues vector model file.");
    }
  }

  public void createDocumentationDataSet() {
    final DocumentationProcessorConfiguration documentationConfiguration =
      configuration.getProcessors().getDocumentation();
    final DocTypeDetector docTypeDetector = new DocTypeDetector();

    final FileRepository fileRepository = new FileRepository();

    final PdfPageSplitter pdfPageSplitter = new PdfPageSplitter();
    final MetadataFileEntryWriter metadataFileEntryWriter = new MetadataFileEntryWriter();
    final DocListFileEntryWriter docListFileEntryWriter = new DocListFileEntryWriter();
    final DataSetFileEntryWriter dataSetFileEntryWriter = new DataSetFileEntryWriter();

    final DocDataSetEntryWriter docDataSetEntryWriter = new DocDataSetEntryWriter(
      fileRepository,
      pdfPageSplitter,
      metadataFileEntryWriter,
      docListFileEntryWriter,
      dataSetFileEntryWriter
    );

    final DocDataSetCreator docDataSetCreator = new DocDataSetCreator(
        documentationConfiguration,
        docDataSetEntryWriter,
        docTypeDetector,
        createAllFileConverter()
    );
    docDataSetCreator.convert();
    try {
      docDataSetCreator.create();
    } catch (IOException e) {
      LOGGER.error("Could not create documentation data set file.", e);
    }
  }

  private Map<MediaType, FileConverter> createAllFileConverter() {
    HashMap<MediaType, FileConverter> fileConverters = new HashMap<>();
    final FileRepository fileRepository = new FileRepository();
    final XwpfPdfConverter pdfConverter = new XwpfPdfConverter();
    fileConverters.put(MediaType.WORD, new WordToPdfFileConverter(
            fileRepository,
            pdfConverter
    )
    );
    fileConverters.put(MediaType.POWERPOINT, new PptPptxToPdfFileConverter(fileRepository));
    fileConverters.put(MediaType.HTML, new HtmlToPdfFileConverter());
    return fileConverters;
  }

  public void createDocumentationVectorModel() {
    final String dataSetFile = configuration.getProcessors().getDocumentation().getDataSetFile();
    final String vectorModelFile = configuration.getProcessors().getDocumentation().getVectorModelFile();
    createVectorModel(dataSetFile, vectorModelFile, "Could not create documentation vector model file.");
  }

  public void createConfluenceDataSet() {
    final ConfluenceConnector confluenceConnector = new ConfluenceConnector(
      configuration.getConnectors().getConfluence());
    try (DataSetWriter<ConfluencePage> confluenceDataSetWriter = new ConfluenceDataSetWriter(
      configuration.getProcessors().getConfluence())) {
      confluenceConnector.createDataSet(confluenceDataSetWriter);
    } catch (IOException e) {
      LOGGER.error("Could not create confluence data set file.", e);
    }
  }

  public void createConfluenceVectorModel() {
    final String dataSetFile = configuration.getProcessors().getConfluence().getDataSetFile();
    final String vectorModelFile = configuration.getProcessors().getConfluence().getVectorModelFile();
    createVectorModel(dataSetFile, vectorModelFile, "Could not create confluence vector model file.");
  }

  public void run() {
    final List<IssueAnalysingResult> results = new ArrayList<>();
    try (IssueClient issueClient = getIssueClient()) {
      final Connector connector = getConnector(issueClient);
      final IssueReceiver receiver = connector.getUnresolvedIssuesReceiver();
      final ProcessExecutor executor = new ProcessExecutor();
      for (final IssueProcessor issueProcessor : getIssueProcessors(issueClient)) {
        executor.add(issueProcessor);
      }
      if (configuration.getAdministration().isParallelExecution()) {
        while (receiver.hasNextIssues()) {
          results.addAll(executor.parallelExecute(receiver.getNextIssues()));
        }
      } else {
        while (receiver.hasNextIssues()) {
          results.addAll(executor.execute(receiver.getNextIssues()));
        }
      }
    } catch (IOException e) {
      LOGGER.error("Could not run issues processing.", e);
    }
    try {
      for (final ReportGenerator reportGenerator : getReportGenerators()) {
        reportGenerator.generate(results);
      }
    } catch (IOException e) {
      LOGGER.error("Could not initialize report generator.", e);
    }
  }

  public void printIssueFields() {
    try (IssueClient issueClient = getIssueClient()) {
      final List<IssueField> fields = issueClient.getIssueFields();
      if (fields.isEmpty()) {
        LOGGER.info("Not exists available fields.");
      } else {
        LOGGER.info("Available fields:");
        if (configuration.getConnectors().getJira().getIssueFieldsCsvFile() == null || configuration.getConnectors().getJira().getIssueFieldsCsvFile().isEmpty()) {
          fields.forEach(field -> LOGGER.info("Field '{}' with id {}", field.getName(), field.getId()));
        } else {
          printIssueFieldsInCsv(fields, configuration.getConnectors().getJira().getIssueFieldsCsvFile());
        }
      }
    } catch (IOException e) {
      LOGGER.error("Could not run issues processing.", e);
    }
  }

  private void printIssueFieldsInCsv(List<IssueField> fields, String filePath) throws IOException {
    StringBuilder fieldsToWrite = new StringBuilder().append("Field" + ',' + "Id" + '\n');
    for (IssueField field : fields) {
      fieldsToWrite.append(field.getName().replaceAll(",", " "))
              .append(',')
              .append(field.getId().replaceAll(",", " "))
              .append('\n');
      LOGGER.info("Field '{}' with id {}", field.getName(), field.getId());
    }
    Path path = Paths.get(filePath);
    if (!Files.exists(path.getParent())) {
      Files.createDirectories(path.getParent()); //NPE if path is wrong
    }
    Files.deleteIfExists(path);
    Files.createFile(path);
    try (FileWriter writeIntoCsv = new FileWriter(path.toAbsolutePath().toString())) {
      writeIntoCsv.write(fieldsToWrite.toString());
      LOGGER.info("The file is recorded and located on the path: " + Paths.get(filePath).toAbsolutePath().toString());
    } catch (IOException e) {
      LOGGER.error("Failed to write file ", e);
    }
  }

  public void runUI() {
    new LauncherUI(this);
  }

  public void testSimilarityProcessor() {
    try {
      new TestSimilarityProcessor().test();
    } catch (IOException e) {
      LOGGER.error("Could not perform similarity processor testing...", e);
    }
  }

  private void createVectorModel(final String dataSetFile, final String vectorModelFile, final String errorMessage) {
    final VectorModelCreator vectorModelCreator = new VectorModelCreator(configuration.getParagraphVectors());
    try {
      final VectorModel vectorModel = vectorModelCreator.createFromFile(new File(dataSetFile));
      final VectorModelSerializer vectorModelSerializer = new VectorModelSerializer();
      vectorModelSerializer.serialize(vectorModel, vectorModelFile);
    } catch (IOException e) {
      LOGGER.error(errorMessage, e);
    }
  }

  private List<IssueProcessor> getIssueProcessors(final IssueClient issueClient) throws IOException {
    final List<String> processorNames = configuration.getAdministration().getProcessors();
    final List<IssueProcessor> issueProcessors = new ArrayList<>();
    if (processorNames != null) {
      if (processorNames.contains(ProcessorConstants.ISSUES.getContent())) {
        issueProcessors.add(new IssuesProcessor(issueClient, configuration));
      }
      if (processorNames.contains(ProcessorConstants.GRANULAR_ISSUES.getContent())) {
        issueProcessors.add(new GranularIssuesProcessor(issueClient, configuration));
      }
      if (processorNames.contains(ProcessorConstants.DOCUMENTATION.getContent())) {
        issueProcessors.add(createDocumentationProcessor());
      }
      if (processorNames.contains(ProcessorConstants.CONFLUENCE.getContent())) {
        issueProcessors.add(new ConfluenceProcessor(configuration));
      }
      if (processorNames.contains(ProcessorConstants.RULE_ENGINE.getContent())) {
        issueProcessors.add(new RuleEngineProcessor(configuration));
      }
    }
    if (processorNames == null || issueProcessors.isEmpty()) {
      LOGGER.error("Could not find any appropriate issue processor in the list: {}", processorNames);
      throw new ConfigurationException("No processor is enabled.", null);
    }
    return issueProcessors;
  }

  private DocumentationProcessor createDocumentationProcessor() throws IOException {
    final VectorModelSerializer vectorModelSerializer = new VectorModelSerializer();
    final File vectorModelFile = new File(configuration.getProcessors().getDocumentation().getVectorModelFile());
    final VectorModel vectorModel = vectorModelSerializer.deserialize(vectorModelFile,
                                                                      configuration.getParagraphVectors()
                                                                                   .getLanguage());

    final FileRepository fileRepository = new FileRepository();
    final DocOutputFilesParser docOutputFilesParser = new DocOutputFilesParser(
      configuration.getProcessors().getDocumentation(),
      fileRepository
    );

    final TextDataExtractor textDataExtractor = new TextDataExtractor();

    return new DocumentationProcessor(
      configuration.getProcessors().getDocumentation(),
      docOutputFilesParser,
      vectorModel,
      textDataExtractor
    );
  }

  private List<ReportGenerator> getReportGenerators() throws IOException {
    final List<String> reporterNames = configuration.getAdministration().getReporters();
    final List<ReportGenerator> reportGenerators = new ArrayList<>();
    if (reporterNames.contains(ReporterConstants.HTML.getContent())) {
      reportGenerators.add(new HtmlReportGenerator(configuration.getReporters().getHtml(),
                                                   configuration.getAdministration().getProcessors()));
    }
    if (reporterNames.contains(ReporterConstants.TEXT.getContent())) {
      reportGenerators.add(new TextReportGenerator(configuration.getReporters().getText()));
    }
    if (reportGenerators.isEmpty()) {
      LOGGER.warn("Could not find any appropriate report generator in the list: {}", reporterNames);
    }
    return reportGenerators;
  }

  private Connector getConnector(final IssueClient issueClient) {
    final ConnectorType connectorType = configuration.getAdministration().getConnectorType();
    switch (connectorType) {
      case JIRA:
        return new JiraConnector(issueClient, configuration.getConnectors().getJira());
      case BUGZILLA:
        return new BugzillaConnector(issueClient, configuration.getConnectors().getBugzilla());
      default:
        throw new IResolverException("Could not get connector with name: " + connectorType);
    }
  }

  private IssueClient getIssueClient() {
    final ConnectorType connectorType = configuration.getAdministration().getConnectorType();
    IssueClientFactory clientFactory;
    switch (connectorType) {
      case JIRA:
        clientFactory = new JiraIssueClientFactory(configuration.getConnectors().getJira());
        break;
      case BUGZILLA:
        clientFactory = new BugzillaIssueClientFactory(configuration.getConnectors().getBugzilla());
        break;
      default:
        throw new IResolverException("Could not get issue client for connector with name: " + connectorType);
    }

    try {
      return clientFactory.getClient();
    } catch (ConnectorException e) {
      throw new IResolverException("Could not initialize client.", e);
    }
  }

  private String getLastSavedIssue(String dataSetFile) {
    String lastIssue = null;
    try (FileReader reader = new FileReader(dataSetFile);
         BufferedReader input = new BufferedReader(reader)) {
      String lastLine = input.readLine();
      String currentLine;
      while ((currentLine = input.readLine()) != null) {
        lastLine = currentLine;
      }
     if (lastLine != null) {
       Pattern pattern = Pattern.compile("^\\S*-\\d*");
       Matcher matcher = pattern.matcher(lastLine);
       if (matcher.find()) {
         lastIssue = matcher.group(0);
       }
     }
    } catch (IOException e) {
      LOGGER.error("Overwrite mode was set, but there is no file to overwrite");
      //throw new ConfigurationException("There is no file with issues to overwrite.", e);
    }
    return lastIssue;
  }

}
