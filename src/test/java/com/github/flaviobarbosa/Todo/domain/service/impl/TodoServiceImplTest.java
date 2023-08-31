package com.github.flaviobarbosa.Todo.domain.service.impl;

import static com.github.flaviobarbosa.Todo.common.TodoConstants.TODO;
import static com.github.flaviobarbosa.Todo.common.TodoConstants.TODO_WITHOUT_ID;
import static com.github.flaviobarbosa.Todo.common.TodoConstants.UPDATED_TODO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.flaviobarbosa.Todo.domain.exception.TodoNotFoundException;
import com.github.flaviobarbosa.Todo.domain.model.Todo;
import com.github.flaviobarbosa.Todo.domain.repository.TodoRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

  @Test
  @DisplayName("Should throw TodoNotFoundException when finding by nonexistent id")
  public void shouldThrowTodoNotFoundExceptionForNonExistentId() {
    when(todoRepository.findById(anyInt())).thenReturn(Optional.empty());
    Assert.assertThrows(TodoNotFoundException.class, () -> todoService.findById(1));
  }

  @DisplayName("Should return all Todos")
  @ParameterizedTest
  @MethodSource
  public void shouldReturnAllTodos(List<Todo> todoList) {
    when(todoRepository.findAll()).thenReturn(todoList);

    List<Todo> sut = todoService.findAll();

    assertThat(sut.size()).isEqualTo(todoList.size());

  }

  private static Stream<Arguments> shouldReturnAllTodos() {
    return Stream.of(
        Arguments.of(Collections.emptyList()),
        Arguments.of(Arrays.asList(
            Todo.builder().id(1).title("Todo 1").description("Description 1").done(false).build(),
            Todo.builder().id(2).title("Todo 2").description("Description 2").done(true).build(),
            Todo.builder().id(3).title("Todo 3").description("Description 3").done(false).build()
        ))
    );
  }

  @Test
  @DisplayName("Should mark Todo as done")
  public void shouldMarkTodoAsDone() {
    Todo todo = Mockito.spy(TODO);
    when(todoRepository.findById(anyInt())).thenReturn(Optional.of(todo));

    todoService.markAsDone(todo.getId());

    verify(todo, times(1)).markAsDone();
    verify(todoRepository, times(1)).save(any(Todo.class));

  }

  @Test
  @DisplayName("Should throw error when marking a nonexistent Todo as done")
  public void shouldThrowErrorWhenMarkingNonexistentTodoAsDone() {
    when(todoRepository.findById(anyInt())).thenReturn(Optional.empty());
    Assert.assertThrows(TodoNotFoundException.class, () -> todoService.markAsDone(1));
  }

  @Test
  @DisplayName("Should update Todo")
  public void shouldUpdateTodo() {
    when(todoRepository.existsById(anyInt())).thenReturn(true);
    when(todoRepository.save(any(Todo.class))).thenReturn(UPDATED_TODO);

    Todo sut = todoService.update(TODO.getId(), TODO);

    assertThat(sut.getId()).isEqualTo(UPDATED_TODO.getId());
    assertThat(sut.getTitle()).isEqualTo(UPDATED_TODO.getTitle());
    assertThat(sut.getDescription()).isEqualTo(UPDATED_TODO.getDescription());
    assertThat(sut.isDone()).isEqualTo(UPDATED_TODO.isDone());
  }

  @Test
  @DisplayName("Should throw error when updating a nonexistent Todo")
  public void shouldThrowErrorWhenWhenUpdatingNonexistentTodo() {
    when(todoRepository.existsById(anyInt())).thenReturn(false);
    Assert.assertThrows(TodoNotFoundException.class, () -> todoService.update(TODO.getId(), TODO));
  }
}