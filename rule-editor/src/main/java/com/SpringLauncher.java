package com;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
//CHECKSTYLE:OFF
public final class SpringLauncher {
    //CHECKSTYLE:ON

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLauncher.class);

    private SpringLauncher() { /*Utility*/ }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SpringLauncher.class, args);

        LOGGER.info("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            LOGGER.info(beanName);
        }
    }

}