package com.cruzdb;

/**
 * Signals that a zlog error has occurred.
 *
 * This exception is used to describe an internal error from the C++ zlog
 * library.
 */
public class BkException extends Exception {
  public BkException(final String msg) {
    super(msg);
  }
}
