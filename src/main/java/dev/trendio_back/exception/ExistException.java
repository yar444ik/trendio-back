package dev.trendio_back.exception;

public class ExistsException extends RuntimeException {

    public ExistsException(String message) {
        super(message);
    }
}
