package com.github.flaviobarbosa.Todo.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.github.flaviobarbosa.Todo.api.model.NewTodoDTO;
import com.github.flaviobarbosa.Todo.api.model.TodoDTO;
import com.github.flaviobarbosa.Todo.domain.model.Todo;
import com.github.flaviobarbosa.Todo.domain.service.TodoService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

  private final ModelMapper mapper;
  private final TodoService todoService;

  @PostMapping
  public ResponseEntity<TodoDTO> create(@RequestBody NewTodoDTO newTodoDTO) {
    Todo todo = mapper.map(newTodoDTO, Todo.class);
    todo = todoService.create(todo);
    TodoDTO dto = mapper.map(todo, TodoDTO.class);
    return ResponseEntity.status(CREATED).body(dto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TodoDTO> findById(@PathVariable int id) {
    Todo todo = todoService.findById(id);
    TodoDTO dto = mapper.map(todo, TodoDTO.class);
    return ResponseEntity.ok(dto);
  }

  @GetMapping
  public ResponseEntity<List<TodoDTO>> findAll() {
    List<Todo> todos = todoService.findAll();
    List<TodoDTO> dtoList = todos.stream().map((todo) -> mapper.map(todo, TodoDTO.class))
        .collect(Collectors.toList());
    return ResponseEntity.ok(dtoList);
  }

  @PutMapping("/{id}/done")
  public ResponseEntity<Void> markAsDone(@PathVariable int id) {
    todoService.markAsDone(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<TodoDTO> update(@PathVariable int id, @RequestBody NewTodoDTO todoDTO) {
    Todo todo = mapper.map(todoDTO, Todo.class);
    Todo updatedTodo = todoService.update(id, todo);
    TodoDTO updatedTodoDTO = mapper.map(updatedTodo, TodoDTO.class);
    return ResponseEntity.ok(updatedTodoDTO);
  }
}
