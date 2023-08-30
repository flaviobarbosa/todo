package com.github.flaviobarbosa.Todo.api.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewTodoDTO {

    private String title;
    private String description;

}
