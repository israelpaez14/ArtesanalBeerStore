package com.artesanalbeer.artesanalbeerstore.exception;

public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }
}
