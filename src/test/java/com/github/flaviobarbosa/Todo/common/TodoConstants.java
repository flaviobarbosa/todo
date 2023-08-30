package com.github.flaviobarbosa.Todo.common;

import com.github.flaviobarbosa.Todo.api.model.NewTodoDTO;
import com.github.flaviobarbosa.Todo.api.model.TodoDTO;
import com.github.flaviobarbosa.Todo.domain.model.Todo;
import java.util.Arrays;
import java.util.List;

public class TodoConstants {

  public static final NewTodoDTO NEW_TODO_DTO = NewTodoDTO.builder().title("Tile")
      .description("description").build();

  public static final TodoDTO TODO_DTO = TodoDTO.builder().id(1).title("Title")
      .description("description").done(false).build();

  public static final Todo TODO_WITHOUT_ID = new Todo("Title", "Description");
  public static final Todo TODO = new Todo(1, "Title", "Description", false);

  public static final List<Todo> TODO_LIST = Arrays.asList(
      new Todo(1, "Title 1", "Description 1", false),
      new Todo(2, "Title 2", "Description 2", false),
      new Todo(3, "Title 3", "Description 3", false)
  );
}
