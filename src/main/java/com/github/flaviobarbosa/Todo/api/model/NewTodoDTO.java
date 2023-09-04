package com.github.flaviobarbosa.Todo.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewTodoDTO {

  @NotBlank
  private String title;

  @NotBlank
  private String description;

}
