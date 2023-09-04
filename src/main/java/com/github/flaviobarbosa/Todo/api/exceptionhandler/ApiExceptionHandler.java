package com.github.flaviobarbosa.Todo.api.exceptionhandler;

import com.github.flaviobarbosa.Todo.domain.exception.TodoNotFoundException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatusCode status, WebRequest request) {

    List<InputError> errors = ex.getFieldErrors()
        .stream()
        .map(fieldError -> new InputError(fieldError.getField(),
            fieldError.getDefaultMessage()))
        .collect(Collectors.toList());

    HttpStatus responseStatus = HttpStatus.UNPROCESSABLE_ENTITY;
    Problem problem = createProblemBuilder(responseStatus, "Invalid input arguments",
        errors);

    return handleExceptionInternal(ex, problem, new HttpHeaders(), responseStatus, request);
  }

  @ExceptionHandler(TodoNotFoundException.class)
  public ResponseEntity<?> handleTodoNotFoundException(TodoNotFoundException ex,
      WebRequest request) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    String detail = ex.getMessage();
    Problem problem = createProblemBuilder(status, detail, null);

    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
  }

  private static Problem createProblemBuilder(HttpStatus status, String detail,
      List<InputError> errors) {
    return Problem.builder()
        .status(status.value())
        .timestamp(OffsetDateTime.now())
        .detail(detail)
        .errors(errors)
        .build();
  }
}
