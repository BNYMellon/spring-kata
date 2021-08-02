/*
 * Copyright 2021 The Bank of New York Mellon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bnymellon.training.spring.boot.todo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import bnymellon.training.spring.boot.todo.model.response.TodoResponse;
import bnymellon.training.spring.boot.todo.model.response.TodoSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import bnymellon.training.spring.boot.todo.model.Todo;
import bnymellon.training.spring.boot.todo.model.response.TodoCollectionResponse;
import bnymellon.training.spring.boot.todo.model.response.TodoErrorResponse;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(
        tags = {"todo-service"}
)
@RequestMapping("/todos")
public interface TodoAPIContract {
    @ApiOperation(
            value = "Create a new todo record",
            notes = "Use this resource to add a new todo record.",
            tags = {"todo-service"}
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Todo record created.", response = TodoSuccessResponse.class),
            @ApiResponse(code = 400, message = "Bad or malformed request", response = TodoErrorResponse.class),
            @ApiResponse(code = 403, message = "User is not entitled to create this todo record.", response =
                    TodoErrorResponse.class),
            @ApiResponse(code = 412, message = "Todo record creation failed, due to validations.", response =
                    TodoErrorResponse.class),
            @ApiResponse(code = 500, message = "An unexpected error has occurred. Todo record was not created.", response =
                    TodoErrorResponse.class),
    })
    @PostMapping(
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE},
            produces = {APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<TodoResponse> addTodo(
            @ApiParam(required = true, value = "A todo record to add")
            @Valid @RequestBody
                    Todo todo,
            HttpServletRequest request);

    //--------------------------------------------------------------------------------------------
    @ApiOperation(
            value = "Fetch multiple todo records",
            notes = "Use this resource to fetch multiple todo records from the repository.",
            tags = {"todo-service"}
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Todo record(s) fetched.", response = TodoCollectionResponse.class),
            @ApiResponse(code = 400, message = "Todo record(s) fetch failed.", response = TodoErrorResponse.class),
            @ApiResponse(code = 403, message = "User is not entitled to fetch todo record(s).", response = TodoErrorResponse
                    .class),
            @ApiResponse(code = 500, message = "An unexpected error has occurred. Todo record(s) was not fetched.", response =
                    TodoErrorResponse.class)})
    @GetMapping(
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE, ALL_VALUE},
            produces = {APPLICATION_JSON_VALUE}
    )
    ResponseEntity<TodoResponse> getTodos(
            @ApiParam(name = "includeInactive", value = "Fetch inactive todos, if true", defaultValue = "false")
                    Boolean includeInactive,
            HttpServletRequest request);

    //--------------------------------------------------------------------------------------------
    @ApiOperation(
            value = "Fetch todo record(s) by assignee",
            notes = "Use this resource to fetch todo records by assignee from the repository.",
            tags = {"todo-service"}
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Todo record fetched.", response = TodoCollectionResponse.class),
            @ApiResponse(code = 400, message = "Todo record fetch failed.", response = TodoErrorResponse.class),
            @ApiResponse(code = 403, message = "User is not entitled to fetch this todo record.", response = TodoErrorResponse
                    .class),
            @ApiResponse(code = 404, message = "Todo record was not found.", response = TodoErrorResponse
                    .class),
            @ApiResponse(code = 500, message = "An unexpected error has occurred. Todo record was not fetched.", response =
                    TodoErrorResponse.class)})
    @GetMapping(
            value = "/assignees/{assignee}",
            produces = {APPLICATION_JSON_VALUE}
    )
    ResponseEntity<TodoResponse> getTodosByAssignee(
            @ApiParam(required = true, value = "Assignee associated with the todo records")
            @PathVariable(value = "assignee")
                    String assignee,
            HttpServletRequest request);

    //--------------------------------------------------------------------------------------------
    @ApiOperation(
            value = "Fetch a todo record by id",
            notes = "Use this resource to fetch a todo record from the repository.",
            tags = {"todo-service"}
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Todo record fetched.", response = TodoSuccessResponse.class),
            @ApiResponse(code = 400, message = "Todo record fetch failed.", response = TodoErrorResponse.class),
            @ApiResponse(code = 403, message = "User is not entitled to fetch this todo record.", response = TodoErrorResponse
                    .class),
            @ApiResponse(code = 404, message = "Todo record was not found.", response = TodoErrorResponse
                    .class),
            @ApiResponse(code = 500, message = "An unexpected error has occurred. Todo record was not fetched.", response =
                    TodoErrorResponse.class)})
    @GetMapping(
            value = "/{todoId}",
            produces = {APPLICATION_JSON_VALUE}
    )
    ResponseEntity<TodoResponse> getTodo(
            @ApiParam(required = true, value = "TodoId representing the todo record")
            @PathVariable(value = "todoId")
                    String todoId,
            HttpServletRequest request);

    //--------------------------------------------------------------------------------------------
    @ApiOperation(
            value = "Update a todo record",
            notes = "Use this resource to update an existing todo record in the repository.",
            tags = {"todo-service"}
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Todo record record updated.", response = TodoSuccessResponse.class),
            @ApiResponse(code = 400, message = "Bad or malformed request", response = TodoErrorResponse.class),
            @ApiResponse(code = 403, message = "User is not entitled to update this todo record.", response =
                    TodoErrorResponse.class),
            @ApiResponse(code = 404, message = "Todo record was not found.", response = TodoErrorResponse.class),
            @ApiResponse(code = 412, message = "Todo record record update failed.", response = TodoErrorResponse
                    .class),
            @ApiResponse(code = 500, message = "An unexpected error has occurred. Todo record was not updated.", response =
                    TodoErrorResponse.class)})
    @PutMapping(
            value = "/{todoId}",
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE},
            produces = {APPLICATION_JSON_VALUE}
    )
    ResponseEntity<TodoResponse> updateTodo(
            @ApiParam(required = true, value = "TodoId representing the todo record") @PathVariable(value = "todoId")
                    String todoId,
            @ApiParam(required = true, value = "A todo record to update") @Valid @RequestBody Todo todo,
            HttpServletRequest request);

    //--------------------------------------------------------------------------------------------
    @ApiOperation(
            value = "Delete a todo record",
            notes = "Use this resource to delete an existing todo record from the repository",
            tags = {"todo-service"}
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Todo record deleted.", response = TodoSuccessResponse.class),
            @ApiResponse(code = 400, message = "Bad or malformed request", response = TodoErrorResponse.class),
            @ApiResponse(code = 403, message = "User is not entitled to delete this todo record.", response =
                    TodoErrorResponse.class),
            @ApiResponse(code = 404, message = "Todo record was not found.", response = TodoErrorResponse.class),
            @ApiResponse(code = 412, message = "Todo record was not deleted.", response = TodoErrorResponse.class),
            @ApiResponse(code = 500, message = "An unexpected error has occurred. Todo record was not deleted.", response =
                    TodoErrorResponse.class)}
    )
    @DeleteMapping(
            value = "/{todoId}",
            produces = {APPLICATION_JSON_VALUE}
    )
    ResponseEntity<TodoResponse> deleteTodo(
            @ApiParam(required = true, value = "TodoId representing the todo record") @PathVariable("todoId")
                    String todoId,
            HttpServletRequest request);
}