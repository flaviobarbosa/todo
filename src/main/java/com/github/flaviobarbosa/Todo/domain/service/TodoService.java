package com.github.flaviobarbosa.Todo.domain.service;

import com.github.flaviobarbosa.Todo.domain.model.Todo;
import java.util.List;

public interface TodoService {

  Todo create(Todo todo);

  Todo findById(int id);

  List<Todo> findAll();

  void markAsDone(int id);

  Todo update(int id, Todo todo);

  void delete(int id);
}
