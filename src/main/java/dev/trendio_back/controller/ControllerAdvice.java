package dev.trendio_back.controller;

import dev.trendio_back.dto.exception.ExceptionMessageDto;
import dev.trendio_back.exception.ExistsException;
import dev.trendio_back.exception.NotFoundException;
import dev.trendio_back.exception.NotUniqException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionMessageDto> notFoundException(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionMessageDto(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionMessageDto> mismatchException(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionMessageDto(exception.getMessage()));
    }

    @ExceptionHandler(ExistsException.class)
    public ResponseEntity<ExceptionMessageDto> existsException(ExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessageDto(exception.getMessage()));
    }

    @ExceptionHandler(NotUniqException.class)
    public ResponseEntity<ExceptionMessageDto> notUniqException(NotUniqException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionMessageDto(exception.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionMessageDto> badCredentialsException(BadCredentialsException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionMessageDto(exception.getMessage()));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionMessageDto> disabledException(DisabledException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ExceptionMessageDto(exception.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionMessageDto> accessDeniedException(AccessDeniedException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ExceptionMessageDto(exception.getMessage()));
    }
}
