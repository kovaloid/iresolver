package com.koval.jresolver.util;

import java.io.Console;
import java.util.Arrays;


public final class CommandLineUtil {

  private CommandLineUtil() {
  }

  public static String getUsername() {
    return getStringFromConsole("Enter your Jira username: ");
  }

  public static String getPassword() {
    return getStringFromConsole("Enter your Jira password: ");
  }

  private static String getStringFromConsole(String question) {
    Console console = System.console();
    if (console == null) {
      throw new RuntimeException("Could not get console instance.");
    }
    return Arrays.toString(console.readPassword(question));
  }
}
