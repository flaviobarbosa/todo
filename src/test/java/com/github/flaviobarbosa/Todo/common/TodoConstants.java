package com.github.flaviobarbosa.Todo.common;

import com.github.flaviobarbosa.Todo.api.model.NewTodoDTO;
import com.github.flaviobarbosa.Todo.domain.model.Todo;

public class TodoConstants {

    public static final NewTodoDTO NEW_TODO_DTO = NewTodoDTO.builder().title("Tile").description("description").build();

    public static final Todo TODO_WITHOUT_ID = new Todo("Title", "Description");
    public static final Todo TODO = new Todo(1, "Title", "Description", false);
}
