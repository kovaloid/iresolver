package com.koval.jresolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
public class JiraResolverApplication {

  public static void main(String[] args) {
    SpringApplication.run(JiraResolverApplication.class, args);
  }
}
