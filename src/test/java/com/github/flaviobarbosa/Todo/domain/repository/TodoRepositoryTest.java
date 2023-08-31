package com.github.flaviobarbosa.Todo.domain.repository;

import static com.github.flaviobarbosa.Todo.common.TodoConstants.TODO_WITHOUT_ID;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.flaviobarbosa.Todo.DatabaseTest;
import com.github.flaviobarbosa.Todo.domain.model.Todo;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = {"/remove_todos.sql",},
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/reset_sequence.sql", "/import_todos.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TodoRepositoryTest extends DatabaseTest {

  @Autowired
  private TodoRepository todoRepository;

  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  @DisplayName("Should save a new Todo")
  public void shouldSaveTodo() {
    Todo todo = todoRepository.save(TODO_WITHOUT_ID);

    Todo sut = testEntityManager.find(Todo.class, todo.getId());

    assertThat(sut).isNotNull();
    assertThat(sut.getTitle()).isEqualTo(TODO_WITHOUT_ID.getTitle());
    assertThat(sut.getDescription()).isEqualTo(TODO_WITHOUT_ID.getDescription());
    assertThat(sut.getId()).isNotNull();

  }

  @Test
  @DisplayName("Should find Todo by id")
  public void shouldFindById() {
    Optional<Todo> optionalTodo = todoRepository.findById(100);

    assertThat(optionalTodo.isPresent());
    assertThat(optionalTodo.get().getId()).isEqualTo(100);
  }

  @Test
  @DisplayName("Given a nonexistent id should return optional empty")
  public void givenNonexistentId_ShouldReturnOptionalEmpty() {
    Optional<Todo> optionalTodo = todoRepository.findById(-1);
    assertThat(optionalTodo.isPresent()).isFalse();
  }

  @Test
  @DisplayName("Should return all Todos")
  public void shouldReturnAllTodos() {
    List<Todo> todoList = todoRepository.findAll();
    int NUMBER_OF_IMPORTED_TODO = 5; //number of inserts from import_todos.sql
    assertThat(todoList.size()).isEqualTo(NUMBER_OF_IMPORTED_TODO);
  }

  @Test
  @DisplayName("Should mark Todo as done")
  public void shouldMarkTodoAsDone() {
    Todo todo = testEntityManager.find(Todo.class, 1);
    todo.markAsDone();
    todoRepository.saveAndFlush(todo);

    Todo sut = testEntityManager.find(Todo.class, 1);

    assertThat(sut.isDone()).isTrue();
  }

  @Test
  @DisplayName("Should update Todo")
  public void shouldUpdateTodo() {
    String updatedTitle = "Updated title";
    String updatedDescription = "Updated description";
    int primaryKey = 1;

    Todo todo = testEntityManager.find(Todo.class, primaryKey);
    todo.setTitle(updatedTitle);
    todo.setDescription(updatedDescription);
    todo.setDone(true);

    todoRepository.saveAndFlush(todo);

    Todo sut = testEntityManager.find(Todo.class, primaryKey);

    assertThat(sut.getId()).isEqualTo(primaryKey);
    assertThat(sut.getTitle()).isEqualTo(updatedTitle);
    assertThat(sut.getDescription()).isEqualTo(updatedDescription);
    assertThat(sut.isDone()).isTrue();
  }
}