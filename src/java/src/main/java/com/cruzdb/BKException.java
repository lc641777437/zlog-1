package com.cruzdb;

/**
 * Signals that a zlog error has occurred.
 *
 * This exception is used to describe an internal error from the C++ zlog
 * library.
 */
public class BKException extends Exception {
  public BKException(final String msg) {
    super(msg);
  }
}
