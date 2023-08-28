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

@DataJpaTest
@ActiveProfiles("test")
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
    Todo todo = todoRepository.save(TODO_WITHOUT_ID);

    Optional<Todo> optionalTodo = todoRepository.findById(todo.getId());

    Todo sut = testEntityManager.find(Todo.class, todo.getId());

    assertThat(optionalTodo.isPresent());
    assertThat(optionalTodo.get()).isEqualTo(sut);
  }
}