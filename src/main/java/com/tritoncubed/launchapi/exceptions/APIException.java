package com.tritoncubed.launchapi.exceptions;

/**
 * A generic class for exceptions that occur in the system. All exceptions should be wrapped in this
 * type and caught by front-facing code.
 */
public class APIException extends RuntimeException {

  private static final long serialVersionUID = 6311400051089321953L;

  public APIException(Exception e) {
    super(e);
  }
	
  public APIException(String message) {
    super(message);
  }
}
