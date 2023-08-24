package com.github.flaviobarbosa.Todo.api.controller;

import static com.github.flaviobarbosa.Todo.common.TodoConstants.NEW_TODO_DTO;
import static com.github.flaviobarbosa.Todo.common.TodoConstants.TODO;
import static com.github.flaviobarbosa.Todo.common.TodoConstants.TODO_WITHOUT_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.flaviobarbosa.Todo.api.model.NewTodoDTO;
import com.github.flaviobarbosa.Todo.domain.model.Todo;
import com.github.flaviobarbosa.Todo.domain.service.TodoService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
  public void shouldCreateTodo() throws Exception {
    given(mapper.map(any(NewTodoDTO.class), eq(Todo.class)))
        .willReturn(TODO_WITHOUT_ID);

    given(todoService.create(any(Todo.class)))
        .willReturn(TODO);

    MockHttpServletRequestBuilder request =
        MockMvcRequestBuilders.post(URI)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(NEW_TODO_DTO));

    mvc.perform(request)
//        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$").value(TODO))
        ;

  }
}
