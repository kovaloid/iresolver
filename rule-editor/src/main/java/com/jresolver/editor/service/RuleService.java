package com.jresolver.editor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class RuleService {

    private final static Logger LOGGER = LoggerFactory.getLogger(RuleService.class);

    public void doSomething() {
        LOGGER.info("Hello from the service!");
    }

}
