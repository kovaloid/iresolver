package com.jresolver.editor.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jresolver.editor.bean.DraftRule;
import com.jresolver.editor.bean.Rule;
import com.jresolver.editor.core.RuleFinder;

@Service
public class RuleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleService.class);

    private final List<Rule> ruleList;
    private Integer maxId;

    public RuleService() throws IOException {
        ruleList = this.getAllRules();
    }

    public Rule getRuleById(int ruleId) {
        LOGGER.info("Retrieving the rule by id");
        for (Rule rule : ruleList) {
            if (rule.getId() == ruleId) {
                return rule;
            }
        }
        return null;
    }

    public List<Rule> getAllRules() throws IOException {
        LOGGER.info("Retrieving all the rules in the system");
        List<Rule> rules = new LinkedList<>();
        Integer i = 0;
        for (final File file : RuleFinder.finder()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line = reader.readLine();
                while (line != null) {
                    if ((line.length() > 4) && line.substring(0, 4).equals("rule")) {
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
                        i++;
                    }
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        maxId = i;
        return rules;
    }

    public Rule updateRuleById(int ruleId, DraftRule payload) {
        LOGGER.info("Updating the rule by its id");
        for (Rule rule : ruleList) {
            if (rule.getId() == ruleId) {
                rule.setName(payload.getName());
                rule.setConditions(payload.getConditions());
                rule.setRecommendations(payload.getRecommendations());
                return rule;
            }
        }
        return null;
    }

    public Rule createRule(DraftRule payload) {
        LOGGER.info("Creating new rule");
        Rule rule = new Rule();
        rule.setName(payload.getName());
        rule.setFile(payload.getName() + ".drl");
        rule.setConditions(payload.getConditions());
        rule.setRecommendations(payload.getRecommendations());
        rule.setId(++maxId);
        ruleList.add(rule);
        return rule;
    }

    public void saveRules(List<Rule> rules) throws IOException {
        Map<String, LinkedList<Rule>> filelist = new HashMap<>();
        for (Rule rule : rules) {
            filelist.putIfAbsent(rule.getFile(), new LinkedList<>());
            filelist.get(rule.getFile()).add(rule);
        }
        for (Map.Entry<String, LinkedList<Rule>> entryList : filelist.entrySet()) {
            List<Rule> rulesFromOneFile = entryList.getValue();
            File drl = new File(RuleFinder.getRulesDir(), rulesFromOneFile.get(0).getFile());
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(drl), StandardCharsets.UTF_8))) {
                writer.write("package com.koval.jresolver.rules\n"
                        + "\n"
                        + "import com.koval.jresolver.connector.bean.JiraIssue;\n"
                        + "import com.koval.jresolver.connector.bean.JiraStatus;\n"
                        + "\n"
                        + "global com.koval.jresolver.rules.results.RulesResult results" + "\n");
                for (final Rule rule : rulesFromOneFile) {
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
}
