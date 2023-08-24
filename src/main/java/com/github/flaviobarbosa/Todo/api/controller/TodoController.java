package com.github.flaviobarbosa.Todo.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.github.flaviobarbosa.Todo.api.model.NewTodoDTO;
import com.github.flaviobarbosa.Todo.api.model.TodoDTO;
import com.github.flaviobarbosa.Todo.domain.model.Todo;
import com.github.flaviobarbosa.Todo.domain.service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
public class TodoController {

  private ModelMapper mapper;
  private TodoService todoService;

  public TodoController(ModelMapper mapper, TodoService todoService) {
    this.mapper = mapper;
    this.todoService = todoService;
  }

  @PostMapping
  public ResponseEntity<Todo> create(@RequestBody NewTodoDTO newTodoDTO) {
    Todo todo = mapper.map(newTodoDTO, Todo.class);
    todo = todoService.create(todo);
    return ResponseEntity.status(CREATED).body(todo);
  }
}
