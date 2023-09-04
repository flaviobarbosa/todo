package com.github.flaviobarbosa.Todo.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {

  private int id;
  private String title;
  private String description;
  private boolean done;

}
