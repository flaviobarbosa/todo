package com.github.flaviobarbosa.Todo.api.exceptionhandler;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(NON_NULL)
@Getter
@Builder
public class Problem {

  private Integer status;
  private OffsetDateTime timestamp;
  private String detail;
  private List<InputError> errors;

}
