package com.github.flaviobarbosa.Todo.api.controller;

import static com.github.flaviobarbosa.Todo.common.TodoConstants.NEW_TODO_DTO;
import static com.github.flaviobarbosa.Todo.common.TodoConstants.TODO;
import static com.github.flaviobarbosa.Todo.common.TodoConstants.TODO_DTO;
import static com.github.flaviobarbosa.Todo.common.TodoConstants.TODO_LIST;
import static com.github.flaviobarbosa.Todo.common.TodoConstants.TODO_WITHOUT_ID;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.flaviobarbosa.Todo.api.model.NewTodoDTO;
import com.github.flaviobarbosa.Todo.api.model.TodoDTO;
import com.github.flaviobarbosa.Todo.domain.exception.TodoNotFoundException;
import com.github.flaviobarbosa.Todo.domain.model.Todo;
import com.github.flaviobarbosa.Todo.domain.service.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

  private static final String URI = "/todo";

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TodoService todoService;

  @MockBean
  private ModelMapper mapper;

  @Test
  @DisplayName("Given valid data, should create a Todo and return created (201)")
  public void shouldCreateTodo() throws Exception {
    given(mapper.map(any(NewTodoDTO.class), eq(Todo.class))).willReturn(TODO_WITHOUT_ID);
    given(todoService.create(any(Todo.class))).willReturn(TODO);
    given(mapper.map(any(Todo.class), eq(TodoDTO.class))).willReturn(TODO_DTO);

    MockHttpServletRequestBuilder request =
        MockMvcRequestBuilders.post(URI)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(NEW_TODO_DTO));

    mvc.perform(request)
//        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").value(TODO_DTO.getId()))
        .andExpect(jsonPath("title").value(TODO_DTO.getTitle()))
        .andExpect(jsonPath("description").value(TODO_DTO.getDescription()))
        .andExpect(jsonPath("done").value(TODO_DTO.isDone()))
    ;
  }

  @Test
  @DisplayName("Given id, should find Todo and return ok (200)")
  public void shouldFindTodoById() throws Exception {
    given(todoService.findById(anyInt())).willReturn(TODO);

    given(mapper.map(any(Todo.class), eq(TodoDTO.class))).willReturn(TODO_DTO);

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI + "/1")
        .accept(APPLICATION_JSON);

    mvc.perform(request)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(TODO_DTO.getId()))
        .andExpect(jsonPath("title").value(TODO_DTO.getTitle()))
        .andExpect(jsonPath("description").value(TODO_DTO.getDescription()))
        .andExpect(jsonPath("done").value(TODO_DTO.isDone()))
    ;
  }

  @Test
  @DisplayName("Given nonexistent id should return not found (404)")
  public void shouldReturnNotFoundForNonexistentId() throws Exception {
    int id = 1;
    doThrow(new TodoNotFoundException(id)).when(todoService).findById(anyInt());

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI + "/1")
        .accept(APPLICATION_JSON);

    mvc.perform(request)
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("status").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("timestamp").isNotEmpty())
        .andExpect(jsonPath("detail").value("Todo with id " + id + " not found"))
    ;
  }

  @Test
  @DisplayName("Should return all Todos")
  public void shouldReturnAllTodos() throws Exception {
    given(todoService.findAll()).willReturn(TODO_LIST);
    given(mapper.map(any(Todo.class), eq(TodoDTO.class))).willReturn(TODO_DTO);

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI)
        .accept(APPLICATION_JSON);

    mvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(TODO_LIST.size())))
    ;
  }

  @Test
  @DisplayName("Should mark Todo as done")
  public void shouldMarkTodoAsDone() throws Exception {
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(URI + "/1/done");
    mvc.perform(request).andExpect(status().isNoContent());

    verify(todoService, times(1)).markAsDone(anyInt());
  }

  @Test
  @DisplayName("Given nonexistent id should return not found (404) when marking as done")
  public void shouldThrowNotFoundWhenMarkingNonexistentTodoAsDone() throws Exception {
    int id = 1;
    doThrow(new TodoNotFoundException(id)).when(todoService).markAsDone(anyInt());

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(URI + "/1/done")
        .accept(APPLICATION_JSON);

    mvc.perform(request)
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("status").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("timestamp").isNotEmpty())
        .andExpect(jsonPath("detail").value("Todo with id " + id + " not found"))
    ;
  }

  @Test
  @DisplayName("Should update Todo")
  public void shouldUpdateTodo() {

  }

  //TODO update nonexistent todo

  @Test
  @DisplayName("Should delete Todo")
  public void shouldDeleteTodo() {

  }

  //TODO delete nonexistent todo
}
