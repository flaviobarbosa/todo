package com.github.flaviobarbosa.Todo.api.exceptionhandler;

import com.github.flaviobarbosa.Todo.domain.exception.TodoNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<?> handleTodoNotFoundException(TodoNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, detail);

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private static Problem createProblemBuilder(HttpStatus status, String detail) {
        return Problem.builder()
                .status(status.value())
                .timestamp(OffsetDateTime.now())
                .detail(detail)
                .build();
    }
}
