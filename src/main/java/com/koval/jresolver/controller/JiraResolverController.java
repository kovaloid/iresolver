package com.koval.jresolver.controller;

import com.koval.jresolver.service.JiraResolverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;


@Controller
public class JiraResolverController {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraResolverController.class);

  @Autowired
  private JiraResolverService service;

  @RequestMapping(value= "/extraction/start", method = RequestMethod.GET)
  public String start(@RequestParam(value="url", required=false) String url,
                      @RequestParam(value="jql", required=false) String jql,
                      @RequestParam(value="maxResults", required=false) int maxResults,
                      @RequestParam(value="startAt", required=false) int startAt,
                      @RequestParam(value="delay", required=false) int delay) throws URISyntaxException {
    LOGGER.info("URL: {}", url);
    LOGGER.info("JQL: {}", jql);
    LOGGER.info("MaxResults: {}", maxResults);
    LOGGER.info("StartAt: {}", startAt);
    LOGGER.info("Delay: {}", delay);
    service.execute(url, jql, maxResults, startAt, delay);
    return "redirect:/index.html";
  }

  @RequestMapping(value= "/extraction/stop", method = RequestMethod.GET)
  public String start() throws URISyntaxException {
    service.stopExecution();
    return "redirect:/";
  }

  @RequestMapping("/extraction/status")
  @ResponseBody
  public double status() {
    return service.getStatus();
  }


  @RequestMapping(value= "/core/train", method = RequestMethod.GET)
  public String train() {
    service.train();
    return "redirect:/";
  }

  @RequestMapping("/core/status")
  @ResponseBody
  public boolean trainStatus() {
    return service.getTrainStatus();
  }
}
