package com.github.flaviobarbosa.Todo.domain.exception;

import lombok.Data;

@Data
public class TodoNotFoundException extends RuntimeException {

    public TodoNotFoundException(int id) {
        super(String.format("Todo with id %d not found", id));
    }
}
