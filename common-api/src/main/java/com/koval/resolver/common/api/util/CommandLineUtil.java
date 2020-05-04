package com.koval.resolver.common.api.util;

import java.io.Console;

import com.koval.resolver.common.api.exception.CommandLineException;


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
      throw new CommandLineException("Could not get console instance.");
    }
    return new String(console.readPassword(question));
  }
}
