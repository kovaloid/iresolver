package com.koval.resolver.common.api.configuration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.koval.resolver.common.api.exception.ConfigurationException;


public final class ConfigurationManager {

  private static final String CONFIGURATION_FILE = "configuration.yml";
  private static final Charset UTF_8 = StandardCharsets.UTF_8;

  private ConfigurationManager() {
  }

  public static Configuration getConfiguration() {
    Yaml yaml = new Yaml(new Constructor(Configuration.class));
    try (InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream(CONFIGURATION_FILE)) {
      return yaml.load(inputStream);
    } catch (Exception e) {
      throw new ConfigurationException("Could not load configuration from the file: " + CONFIGURATION_FILE, e);
    }
  }
}
