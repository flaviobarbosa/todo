package com.github.flaviobarbosa.Todo.domain.service;

import com.github.flaviobarbosa.Todo.domain.model.Todo;
import java.util.List;

public interface TodoService {

  Todo create(Todo todo);

  Todo findById(int id);

  List<Todo> findAll();
}
