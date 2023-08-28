package com.github.flaviobarbosa.Todo.domain.service.impl;

import static com.github.flaviobarbosa.Todo.common.TodoConstants.TODO;
import static com.github.flaviobarbosa.Todo.common.TodoConstants.TODO_WITHOUT_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.github.flaviobarbosa.Todo.domain.model.Todo;
import com.github.flaviobarbosa.Todo.domain.repository.TodoRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
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
  @DisplayName("Should create a Todo")
  public void shouldCreateTodo() {
    when(todoRepository.save(Mockito.any(Todo.class)))
        .thenReturn(TODO);

    Todo sut = todoService.create(TODO_WITHOUT_ID);

    assertThat(sut).isEqualTo(TODO);
  }

  @Test
  @DisplayName("Should find Todo by id")
  public void shouldFindTodoById() {
    when(todoRepository.findById(anyInt())).thenReturn(Optional.of(TODO));

    Todo sut = todoService.findById(1);

    assertThat(sut).isEqualTo(TODO);
  }
}