package dev.trendio_back.exception;

public class NotUniqException extends RuntimeException {

    public NotUniqException(String message) {
        super(message);
    }
}