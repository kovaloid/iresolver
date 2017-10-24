package com.koval.jresolver.controller;

import com.koval.jresolver.service.JiraResolverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;


@Controller
public class JiraResolverController {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraResolverController.class);

  @Autowired
  private JiraResolverService service;

  @RequestMapping("/trigger")
  @ResponseBody
  public void startWork() throws URISyntaxException, InterruptedException {
    service.execute("", "", "");
  }

  @RequestMapping("/status")
  @ResponseBody
  int status() throws URISyntaxException {
    return service.getStatus();
  }
}
