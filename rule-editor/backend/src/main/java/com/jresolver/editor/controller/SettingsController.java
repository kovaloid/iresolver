package com.jresolver.editor.controller;

import com.jresolver.editor.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SettingsController {

    @Autowired
    private RuleService ruleService;

    @RequestMapping("/rest/settings/location")
    public String index() {
        return "The location of newly created rules was saved";
    }
}
