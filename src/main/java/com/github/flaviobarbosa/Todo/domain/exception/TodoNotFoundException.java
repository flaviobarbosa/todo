package com.github.flaviobarbosa.Todo.domain.exception;

public class TodoNotFoundException extends RuntimeException {

  public TodoNotFoundException(int id) {
    super(String.format("Todo with id %d not found", id));
  }
}
