package com.github.flaviobarbosa.Todo.api.openapi;

import com.github.flaviobarbosa.Todo.api.model.NewTodoDTO;
import com.github.flaviobarbosa.Todo.api.model.TodoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;

@Tag(name = "Todo Controller")
public interface TodoControllerOpenApi {

  @Operation(
      summary = "Create Todo",
      responses = {
          @ApiResponse(responseCode = "200", description = "Todo successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TodoDTO.class))),
          @ApiResponse(responseCode = "422", description = "Invalid input", content = @Content)
      }
  )
  ResponseEntity<TodoDTO> create(@Parameter(required = true) NewTodoDTO newTodoDTO);

  @Operation(
      summary = "Get Todo by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Found Todo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TodoDTO.class))),
          @ApiResponse(responseCode = "404", description = "Todo not found", content = @Content)
      }
  )
  ResponseEntity<TodoDTO> findById(
      @Parameter(description = "Todo id", required = true, example = "1") int id);

  @Operation(
      summary = "Get all Todos",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "List of all Todos found",
              content = @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = TodoDTO.class)))
          )
      }
  )
  ResponseEntity<List<TodoDTO>> findAll();

  @Operation(
      summary = "Mark Todo as done",
      responses = {
          @ApiResponse(responseCode = "204", description = "Todo marked as done"),
          @ApiResponse(responseCode = "404", description = "Todo not found")
      }
  )
  ResponseEntity<Void> markAsDone(
      @Parameter(description = "Todo id", required = true, example = "1") int id);

  @Operation(
      summary = "Update Todo",
      responses = {
          @ApiResponse(responseCode = "200", description = "Todo successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TodoDTO.class))),
          @ApiResponse(responseCode = "404", description = "Todo not found", content = @Content)
      }
  )
  ResponseEntity<TodoDTO> update(
      @Parameter(description = "Todo id", required = true, example = "1") int id,
      @Parameter(required = true) NewTodoDTO todoDTO);

  @Operation(
      summary = "Delete Todo",
      responses = {
          @ApiResponse(responseCode = "204", description = "Todo successfully deleted")
      }
  )
  void delete(@Parameter(description = "Todo id", required = true, example = "1") int id);
}
