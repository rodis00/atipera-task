package com.rodis00.atiperatask.exception;

public class GithubUserNotFoundException extends RuntimeException {
  public GithubUserNotFoundException(String message) {
    super(message);
  }
}
