package com.example.pokedexapi.config;

import com.example.pokedexapi.config.exception.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, ServletWebRequest request) {
        log.error("Validation error: {}", ex.getMessage());
        List<String> errors = ex.getConstraintViolations()
                .stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, String.join(",", errors));
        problemDetail.setTitle("Validation error");
        problemDetail.setInstance(URI.create(request.getRequest().getRequestURI()));
        problemDetail.setType(URI.create("/pokemon/bad-request"));
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException e, ServletWebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Pokemon not found");
        problemDetail.setType(URI.create("/pokemon/not-found"));
        problemDetail.setInstance(URI.create(request.getRequest().getRequestURI()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }
}
