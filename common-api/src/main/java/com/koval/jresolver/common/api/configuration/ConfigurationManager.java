package com.koval.jresolver.common.api.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;


public class ConfigurationManager {

  public Configuration getConfiguration() throws IOException {
    Yaml yaml = new Yaml(new Constructor(Configuration.class));
    //InputStream inputStream = Configuration.class.getClass()
    //    .getClassLoader()
    //    .getResourceAsStream("configuration.yml");

    try (InputStream inputStream = new FileInputStream("C:\\jresolver\\common-api\\src\\main\\resources\\configuration.yml")) {
      //Map<String, Object> obj = yaml.load(inputStream);
      return yaml.load(inputStream);
    }
  }
}
