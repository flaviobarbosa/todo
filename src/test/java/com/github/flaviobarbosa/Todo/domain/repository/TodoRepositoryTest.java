package com.github.flaviobarbosa.Todo.domain.repository;

import static com.github.flaviobarbosa.Todo.common.TodoConstants.TODO_WITHOUT_ID;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.flaviobarbosa.Todo.DatabaseTest;
import com.github.flaviobarbosa.Todo.domain.model.Todo;
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
@Sql(scripts = {"/remove_todos.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/import_todos.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
    Optional<Todo> optionalTodo = todoRepository.findById(10);
    assertThat(optionalTodo.isPresent()).isFalse();
  }
}