package com.github.flaviobarbosa.Todo.domain.service.impl;

import com.github.flaviobarbosa.Todo.domain.model.Todo;
import com.github.flaviobarbosa.Todo.domain.repository.TodoRepository;
import com.github.flaviobarbosa.Todo.domain.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;

  @Override
  public Todo create(Todo todo) {
    return todoRepository.save(todo);
  }
}
