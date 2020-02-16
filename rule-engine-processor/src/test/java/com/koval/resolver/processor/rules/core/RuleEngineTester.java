package com.koval.resolver.processor.rules.core;

public class RuleEngineTester {
/*
    @SuppressWarnings("VisibilityModifier")
    private final InputStream stream = new ByteArrayInputStream("global RulesResult results".getBytes(StandardCharsets.UTF_8));
    private final InputStream badStream = new ByteArrayInputStream("test syntax mistake in rule".getBytes(StandardCharsets.UTF_8));

    @Test
    public void testCreating() throws Exception {
        boolean flag = true;
        try {
            Resource[] resources = new Resource[1];
            resources[0] = new InputStreamResource(stream);
            DroolsRuleEngine test = new DroolsRuleEngine(resources);
            test.close();
        } catch (Exception e) { //Not IOException, becouse close() throws Exception
            flag = false;
        }

        assertTrue(flag);
    }

    @Test
    public void testExecuting() throws Exception {
        JiraIssue issue = new JiraIssue();
        issue.setKey("test_issue");
        issue.setDescription("test_description");
        issue.setComments(new ArrayList<>());

        boolean flag = true;
        try {
            Resource[] resources = new Resource[1];
            resources[0] = new InputStreamResource(stream);
            DroolsRuleEngine test = new DroolsRuleEngine(resources);
            test.execute(issue);
            test.close();
        } catch (Exception e) {
            flag = false;
        }

        assertTrue(flag);
    }

    @Test
    public void testSettingDebugMode() throws Exception {
        boolean flag = true;
        try {
            Resource[] resources = new Resource[1];
            resources[0] = new InputStreamResource(stream);
            DroolsRuleEngine test = new DroolsRuleEngine(resources);
            test.setDebugMode();
            test.close();
        } catch (Exception e) {
            flag = false;
        }

        assertTrue(flag);
    }

    @Test
    public void testIfNoOneRule() throws Exception {
        boolean flag = true;
        try {
            Resource[] resources = new Resource[0];
            DroolsRuleEngine test = new DroolsRuleEngine(resources);
            test.close();
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Could not find any *.drl files.")) {
                flag = true;
            } else {
                flag = false;
            }
        }

        assertTrue(flag);
    }

    @Test
    public void testIfRuleWithMistake() throws Exception {
        boolean flag = true;
        try {
            Resource[] resources = new Resource[1];
            resources[0] = new InputStreamResource(badStream);
            DroolsRuleEngine test = new DroolsRuleEngine(resources);
            test.close();
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Unable to compile *.drl files.")) {
                flag = true;
            } else {
                flag = false;
            }
        }

        assertTrue(flag);
    }*/
}
