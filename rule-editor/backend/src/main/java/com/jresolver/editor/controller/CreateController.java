package com.jresolver.editor.controller;

import com.jresolver.editor.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public final class CreateController {
    @Autowired
    private RuleService ruleService;

    @RequestMapping("/rest/create/save")
    public String index() {
        ruleService.doSomething();
        return "New rule was saved!";
    }

    @RequestMapping("/rest/create/cancel")
    public String helloPage() {
        return "Cancel creating the rule";
    }
}
