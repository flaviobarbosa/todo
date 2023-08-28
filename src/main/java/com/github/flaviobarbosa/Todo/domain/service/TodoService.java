package com.github.flaviobarbosa.Todo.domain.service;

import com.github.flaviobarbosa.Todo.domain.model.Todo;

public interface TodoService {

  Todo create(Todo todo);

  Todo findById(int id);
}
