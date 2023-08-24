package com.github.flaviobarbosa.Todo.domain.service.impl;

import static com.github.flaviobarbosa.Todo.common.TodoConstants.TODO;
import static com.github.flaviobarbosa.Todo.common.TodoConstants.TODO_WITHOUT_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.github.flaviobarbosa.Todo.common.TodoConstants;
import com.github.flaviobarbosa.Todo.domain.model.Todo;
import com.github.flaviobarbosa.Todo.domain.repository.TodoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

  @InjectMocks
  private TodoServiceImpl todoService;

  @Mock
  private TodoRepository todoRepository;

  @Test
  public void shouldCreateTodo() {
    when(todoRepository.save(Mockito.any(Todo.class)))
        .thenReturn(TodoConstants.TODO);

    Todo sut = todoService.create(TODO_WITHOUT_ID);

    Assertions.assertThat(sut).isEqualTo(TODO);
  }
}