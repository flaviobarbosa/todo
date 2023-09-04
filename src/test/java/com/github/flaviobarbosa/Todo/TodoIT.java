package com.github.flaviobarbosa.Todo;

import static com.github.flaviobarbosa.Todo.common.TodoConstants.EMPTY_TODO_DTO;
import static com.github.flaviobarbosa.Todo.common.TodoConstants.NEW_TODO_DTO;
import static com.github.flaviobarbosa.Todo.common.TodoConstants.UPDATED_TODO_DTO;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.flaviobarbosa.Todo.api.model.TodoDTO;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/remove_todos.sql",},
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/reset_sequence.sql", "/import_todos.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TodoIT {

  private final static String URL = "/todo";

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @DisplayName("Create Todo should return created (201)")
  public void createTodo_ReturnsCreated() {
    ResponseEntity<TodoDTO> sut = restTemplate.postForEntity(URL, NEW_TODO_DTO,
        TodoDTO.class);

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(sut.getBody().getId()).isNotNull();
    assertThat(sut.getBody().getTitle()).isEqualTo(NEW_TODO_DTO.getTitle());
    assertThat(sut.getBody().getDescription()).isEqualTo(NEW_TODO_DTO.getDescription());
    assertThat(sut.getBody().isDone()).isFalse();
  }

  @Test
  @DisplayName("Create Todo with invalid data should return unprocessable entity (422)")
  public void createTodo_WithInvalidData_ReturnsUnprocessableEntity() {
    ResponseEntity<TodoDTO> sut = restTemplate.postForEntity(URL, EMPTY_TODO_DTO,
        TodoDTO.class);

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @Test
  @DisplayName("List Todo should return all Todos")
  public void listTodo_ReturnsAllTodos() {
    ResponseEntity<TodoDTO[]> sut = restTemplate.getForEntity(URL, TodoDTO[].class);

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(sut.getBody().length).isEqualTo(5);
  }

  @Test
  @DisplayName("Get Todo by id should return Ok")
  public void getTodoById_ReturnsOk() {
    int id = 1;
    ResponseEntity<TodoDTO> sut = restTemplate.getForEntity(URL + "/" + id, TodoDTO.class);

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(sut.getBody().getId()).isEqualTo(id);
    assertThat(sut.getBody().getTitle()).isEqualTo("Todo 1");
    assertThat(sut.getBody().getDescription()).isEqualTo("Description of todo 1");
    assertThat(sut.getBody().isDone()).isFalse();
  }

  @Test
  @DisplayName("Mark Todo as done should return No Content")
  public void markTodoAsDone_returnsNoContent() {
    ResponseEntity<Void> sut = restTemplate.exchange(URL + "/1/done", HttpMethod.PUT, null,
        Void.class);

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  @DisplayName("Update Todo should return Ok")
  public void updateTodo_ReturnsOk() {
    ResponseEntity<TodoDTO> sut = restTemplate.exchange(URL + "/1", HttpMethod.PUT,
        new HttpEntity(UPDATED_TODO_DTO), TodoDTO.class, new HashMap<>());

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(sut.getBody().getId()).isEqualTo(UPDATED_TODO_DTO.getId());
    assertThat(sut.getBody().getTitle()).isEqualTo(UPDATED_TODO_DTO.getTitle());
    assertThat(sut.getBody().getDescription()).isEqualTo(UPDATED_TODO_DTO.getDescription());
    assertThat(sut.getBody().isDone()).isFalse();
  }

  @Test
  @DisplayName("Delete Todo should return No Content")
  public void deleteTodo_ReturnsNoContent() {
    ResponseEntity<Void> sut = restTemplate.exchange(URL + "/1", HttpMethod.DELETE, null,
        Void.class);

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }
}
