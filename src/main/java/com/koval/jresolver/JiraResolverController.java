package com.koval.jresolver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class JiraResolverController {

  @RequestMapping("/home")
  @ResponseBody
  String home() {
    return "Hello World!";
  }
}
