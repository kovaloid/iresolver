package com.koval.jresolver.controller;


import com.koval.jresolver.service.Doc2vecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class Doc2vecController {

  @Autowired
  private Doc2vecService service;

  @RequestMapping(value= "/core/doc2vec/train", method = RequestMethod.GET)
  public String train() {
    service.train();
    return "redirect:/";
  }

  @RequestMapping("/core/doc2vec/result")
  @ResponseBody
  public String use(@RequestParam(value="text", required=false) String text,
                    @RequestParam(value="num", required=false) int num) {
    return service.use(text, num).toString();
  }

  @RequestMapping("/core/doc2vec/status")
  @ResponseBody
  public String status() {
    return service.status();
  }
}
