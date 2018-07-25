package com.jresolver.editor.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jresolver.editor.bean.DraftRule;
import com.jresolver.editor.bean.Metadata;
import com.jresolver.editor.bean.Rule;
import com.jresolver.editor.core.RuleFinder;

@Service
@SuppressWarnings("PMD") //Cyclomatic complexity ¯\_(ツ)_/¯
public class RuleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleService.class);


    private Map<Integer, Metadata> metadataMap;
    private List<Rule> ruleList;
    private Integer maxId;

    public RuleService() throws IOException {
        readRulesFromDrive();
    }

    public Rule getRuleById(int ruleId) {
        LOGGER.info("Retrieving the rule by id: {}", ruleId);
        for (Rule rule : ruleList) {
            if (rule.getId() == ruleId) {
                return rule;
            }
        }
        return null;
    }

    public List<Rule> getRulesList() {
        return ruleList;
    }

    public Rule updateRuleById(int ruleId, DraftRule payload) {
        LOGGER.info("Updating the rule by id: {}...", ruleId);
        Metadata metadata = metadataMap.get(ruleId);
        if (metadata.getFile() == null) {
            metadata.setFile(payload.getFile());
            metadata.setModified(true);
            Rule rule = new Rule();
            rule.setId(ruleId);
            updateRule(rule, payload);
            ruleList.add(rule);
            LOGGER.info("...empty rule updated! Rule name: {}", rule.getName());
            return rule;
        } else {
            for (Rule rule : ruleList) {
                if (rule.getId() == ruleId) {
                    updateRule(rule, payload);
                    metadata.setFile(payload.getFile());
                    metadata.setModified(true);
                    LOGGER.info("...rule updated! Rule name: {}", rule.getName());
                    return rule;
                }
            }
        }
        return null;
    }

    public Rule createRule(DraftRule payload) {
        LOGGER.info("Creating rule from payload");
        Rule rule = new Rule();
        updateRule(rule, payload);
        rule.setId(++maxId);
        ruleList.add(rule);
        Metadata metadata = new Metadata();
        //metadata.setFile(payload.getFile());
        metadata.setModified(true);
        metadataMap.put(maxId, metadata);
        return rule;
    }

    public Rule getNewRule() {
        LOGGER.info("Creating new empty rule");
        Rule rule = new Rule();
        rule.setId(++maxId);
        metadataMap.put(maxId, new Metadata());
        return rule;
    }

    public Rule deleteRule(int ruleId) {
        LOGGER.info("Deleting rule by id: {}", ruleId);
        metadataMap.remove(ruleId);
        for (Rule rule : ruleList) {
            if (rule.getId() == ruleId) {
                ruleList.remove(rule);
                LOGGER.info("Deleted successful: ", rule.getName());
                return rule;
            }
        }
        return null;
    }

    public void saveRulesToDrive() throws IOException {
        LOGGER.info("Saving rules to drive!");

        Map<String, LinkedList<Rule>> filelist = new HashMap<>();
        for (Metadata metadata : metadataMap.values()) {
            if (metadata.getFile() != null && metadata.isModified()) {
                filelist.putIfAbsent(metadata.getFile(), new LinkedList<>());
            }
        }

        for (Rule rule : ruleList) {
            List<Rule> list = filelist.get(rule.getFile());
            if (list != null) {
                list.add(rule);
            }
        }

        for (Map.Entry<String, LinkedList<Rule>> entryList : filelist.entrySet()) {
            List<Rule> rulesFromOneFile = entryList.getValue();
            LOGGER.debug("File: {}", entryList.getKey());
            File drl = new File(RuleFinder.getRulesDir(), rulesFromOneFile.get(0).getFile());
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(drl), StandardCharsets.UTF_8))) {
                writer.write("package com.koval.jresolver.rules\n"
                    + "\n"
                    + "import com.koval.jresolver.connector.bean.JiraIssue;\n"
                    + "import com.koval.jresolver.connector.bean.JiraStatus;\n"
                    + "\n"
                    + "global com.koval.jresolver.rules.results.RulesResult results" + "\n");
                for (final Rule rule : rulesFromOneFile) {
                    LOGGER.debug("-rule: {}", rule.getName());
                    writer.append("\nrule \"").append(rule.getName()).append("\"\n\t");
                    if (rule.getAttributes() != null && rule.getAttributes().size() > 0) {
                        for (final String att : rule.getAttributes()) {
                            writer.append(att).append("\n\t");
                        }
                    }
                    writer.append("when\n");
                    for (final String condition : rule.getConditions()) {
                        writer.append("\t\t").append(condition).append('\n');
                    }
                    writer.append("\tthen\n");
                    for (final String recommendation : rule.getRecommendations()) {
                        writer.append("\t\t").append(recommendation).append('\n');
                    }
                    writer.append("end\n");
                }
            }
        }
    }

    private void readRulesFromDrive() throws IOException {
        LOGGER.info("Retrieving all the rules in the system");
        Map<Integer, Metadata> data = new HashMap<>();
        List<Rule> rules = new LinkedList<>();
        Integer i = 0;
        for (final File file : RuleFinder.finder()) {
            LOGGER.debug("File: {}", file.getName());
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line = reader.readLine();
                while (line != null) {
                    if ((line.length() > 4) && line.substring(0, 4).equals("rule")) {
                        Metadata metadata = new Metadata();
                        metadata.setFile(file.getName());
                        data.put(i, metadata);
                        Rule rule = new Rule();
                        rule.setId(i);
                        rule.setFile(file.getName());
                        rule.setName(line.substring(line.indexOf('"') + 1, line.length() - 1));
                        line = reader.readLine();
                        List<String> attributes = new LinkedList<>();
                        while (!"\twhen".equals(line)) {
                            attributes.add(line.substring(1));
                            line = reader.readLine();
                        }
                        rule.setAttributes(attributes);
                        line = reader.readLine();
                        List<String> conditions = new LinkedList<>();
                        while (!"\tthen".equals(line)) {
                            conditions.add(line.substring(2));
                            line = reader.readLine();
                        }
                        rule.setConditions(conditions);
                        line = reader.readLine();
                        List<String> recommendations = new LinkedList<>();
                        while (!"end".equals(line)) {
                            recommendations.add(line.substring(2));
                            line = reader.readLine();
                        }
                        rule.setRecommendations(recommendations);
                        rules.add(rule);
                        LOGGER.debug("-rule: {}", rule.getName());
                        i++;
                    }
                    line = reader.readLine();
                }
            }
        }
        maxId = i;

        ruleList = rules;
        metadataMap = data;
    }

    private void updateRule(Rule rule, DraftRule payload) {
        rule.setName(payload.getName());
        rule.setFile(payload.getFile());
        rule.setAttributes(payload.getAttributes());
        rule.setConditions(payload.getConditions());
        rule.setRecommendations(payload.getRecommendations());
    }
}

