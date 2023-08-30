package com.github.flaviobarbosa.Todo.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@Getter
@Builder
public class Problem {

    private Integer status;
    private OffsetDateTime timestamp;
    private String detail;

}
