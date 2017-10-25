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

  @RequestMapping(value= "/trigger", method = RequestMethod.GET)
  @ResponseBody
  public void startWork(@RequestParam(value="url", required=false) String url,
                        @RequestParam(value="user", required=false) String user,
                        @RequestParam(value="pass", required=false) String pass) throws URISyntaxException, InterruptedException {
    service.execute(url, user, pass);
  }

  @RequestMapping("/status")
  @ResponseBody
  int status() throws URISyntaxException {
    return service.getStatus();
  }
}
