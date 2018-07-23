package com.jresolver.editor.service;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.jresolver.editor.bean.Rule;
import org.springframework.stereotype.Service;


@Service
public final class RuleManager {

    public void saveRules(List<Rule> rules) throws IOException {
        Map<String, LinkedList<Rule>> filelist = new HashMap<>();
        for (Rule rule : rules) {
            filelist.putIfAbsent(rule.getFile(), new LinkedList<>());
            filelist.get(rule.getFile()).add(rule);
        }
        for (String filename : filelist.keySet()) {
            List<Rule> rulesFromOneFile = filelist.get(filename);
            File drl = new File(getRulesDir(), rulesFromOneFile.get(0).getFile());
            try (FileWriter writer = new FileWriter(drl)) {
                writer.write("package com.koval.jresolver.rules\n" +
                        "\n" +
                        "import com.koval.jresolver.connector.bean.JiraIssue;\n" +
                        "import com.koval.jresolver.connector.bean.JiraStatus;\n" +
                        "\n" +
                        "global com.koval.jresolver.rules.results.RulesResult results" + "\n");
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

    public List<Rule> getRules() throws IOException {
        List<Rule> rules = new LinkedList<>();

        for (final File file : finder()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                while (line != null) {
                    if ((line.length() > 4) && line.substring(0, 4).equals("rule")) {
                        Rule rule = new Rule();
                        rule.setFile(file.getName());
                        rule.setName(line.substring(line.indexOf('"') + 1, line.length() - 1));

                        line = reader.readLine();
                        List<String> attributes = new LinkedList<>();
                        while (!line.equals("\twhen")) {
                            attributes.add(line.substring(1));
                            line = reader.readLine();
                        }
                        rule.setAttributes(attributes);

                        line = reader.readLine();
                        List<String> conditions = new LinkedList<>();
                        while (!line.equals("\tthen")) {
                            conditions.add(line.substring(2));
                            line = reader.readLine();
                        }
                        rule.setConditions(conditions);

                        line = reader.readLine();
                        List<String> recommendations = new LinkedList<>();
                        while (!line.equals("end")) {
                            recommendations.add(line.substring(2));
                            line = reader.readLine();
                        }
                        rule.setRecommendations(recommendations);
                        rules.add(rule);
                    }
                    line = reader.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rules;
    }

    private File[] finder() throws IOException {
        return getRulesDir().listFiles((dir1, filename) -> filename.endsWith(".drl"));
    }

    private File getRulesDir() throws IOException {
        if (new File("rules").exists()) {
            return new File("rules");
        } else if (new File("..\\rules").exists()) {
            return new File("..\\rules");
        } else if (new File("src/main/resources/rules").exists()) {
            return new File("src/main/resources/rules");
        }
        throw new IOException("Rules folder has not found");
    }
}
