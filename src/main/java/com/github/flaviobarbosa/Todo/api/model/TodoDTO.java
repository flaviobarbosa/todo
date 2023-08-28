package com.github.flaviobarbosa.Todo.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TodoDTO {

  private int id;
  private String title;
  private String description;
  private boolean done;

}
