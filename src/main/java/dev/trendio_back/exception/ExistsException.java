package dev.trendio_back.exception;

//todo handle postgresql exists exception
public class ExistsException extends RuntimeException {

    public ExistsException(String message) {
        super(message);
    }
}
