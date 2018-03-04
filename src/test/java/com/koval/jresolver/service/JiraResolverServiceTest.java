package com.koval.jresolver.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JiraResolverServiceTest {

  @Autowired
  private JiraResolverService service;

  @Test
  public void execute() throws Exception {
    service.execute("https://issues.apache.org/jira", "project = AMQ", 5, 0, 3000);
  }

}