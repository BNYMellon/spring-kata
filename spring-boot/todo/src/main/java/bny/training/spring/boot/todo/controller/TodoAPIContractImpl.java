/*
 * Copyright 2024 The Bank of New York Mellon.
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

package bny.training.spring.boot.todo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import bny.training.spring.boot.todo.model.Todo;
import bny.training.spring.boot.todo.model.exception.InvalidAssigneeException;
import bny.training.spring.boot.todo.model.exception.InvalidIdException;
import bny.training.spring.boot.todo.model.exception.TodoNotFoundException;
import bny.training.spring.boot.todo.service.TodoService;
import bny.training.spring.boot.todo.model.response.Issue;
import bny.training.spring.boot.todo.model.response.TodoErrorResponse;
import bny.training.spring.boot.todo.model.response.TodoResponse;
import bny.training.spring.boot.todo.model.response.TodoSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bny.training.spring.boot.todo.model.response.Metadata;
import bny.training.spring.boot.todo.model.response.Status;
import bny.training.spring.boot.todo.model.response.TodoCollectionResponse;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/todos")
public class TodoAPIContractImpl implements TodoAPIContract {

    private TodoService todoService;
    private TodoResponseHelper todoResponseHelper;

    @Autowired
    public TodoAPIContractImpl(final TodoService todoService) {

        this.todoService = todoService;
        todoResponseHelper = new TodoResponseHelper();
    }

    @PostMapping(
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE},
            produces = {APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TodoResponse> addTodo(
            @Valid @RequestBody Todo todo,
            HttpServletRequest request) {

        Todo todoResponse = todoService.saveTodo(todo);

        Metadata metadata = todoResponseHelper.createRequestMetadata(request);

        Status status = todoResponseHelper.createSuccessStatus();
        status.setStatusCode(HttpStatus.CREATED.getReasonPhrase());

        TodoSuccessResponse response = new TodoSuccessResponse();
        response.setMetadata(metadata);
        response.setTodo(todoResponse);
        response.getMetadata().setStatus(status);

        todoResponseHelper.updateMetadata(response.getMetadata());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE, ALL_VALUE},
            produces = {APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<TodoResponse> getTodos(
            @RequestParam(name = "includeInactive", required = false, defaultValue = "false")
                    Boolean includeInactive,
            HttpServletRequest request) {

        TodoCollectionResponse response = new TodoCollectionResponse();
        // Convert the Boolean to a primitive boolean.
        response.setTodos(todoService.getAllTodos(includeInactive));

        Metadata metadata = createRequestMetadata(request);
        response.setMetadata(metadata);
        response.getMetadata().setStatus(createSuccessStatus());

        return ResponseEntity.ok(response);
    }

    @RequestMapping(
            value = "/assignees/{assignee}",
            method = RequestMethod.GET,
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE, ALL_VALUE},
            produces = {APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<TodoResponse> getTodosByAssignee(
            @PathVariable(value = "assignee")
                    String assignee,
            HttpServletRequest request) {


        TodoCollectionResponse response = new TodoCollectionResponse();
        // Convert the Boolean to a primitive boolean.
        response.setTodos(todoService.getTodosByAssignee(assignee));

        Metadata metadata = createRequestMetadata(request);
        response.setMetadata(metadata);
        response.getMetadata().setStatus(createSuccessStatus());

        return ResponseEntity.ok(response);
    }

    @RequestMapping(
            value = "/{todoId}",
            method = RequestMethod.GET,
            produces = {APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<TodoResponse> getTodo(
            @PathVariable(value = "todoId")
                    String todoId,
            HttpServletRequest request) {

        // Convert the String to a Long.
        Todo todoFetched = todoService.getTodo(todoId);

        Metadata metadata = todoResponseHelper.createRequestMetadata(request);

        TodoSuccessResponse response = new TodoSuccessResponse();
        response.setMetadata(metadata);
        response.setTodo(todoFetched);
        response.getMetadata().setStatus(todoResponseHelper.createSuccessStatus());

        todoResponseHelper.updateMetadata(response.getMetadata());

        return ResponseEntity.ok(response);
    }

    @Override
    @RequestMapping(
            value = "/{todoId}",
            method = RequestMethod.PUT,
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE},
            produces = {APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<TodoResponse> updateTodo(
            @PathVariable(value = "todoId")
                    String todoId,
            @Valid @RequestBody Todo todo,
            HttpServletRequest request) throws InvalidIdException, TodoNotFoundException {

        // Convert the String to a Long.
        Todo todoFetched = todoService.updateTodo(todoId, todo);

        Metadata metadata = todoResponseHelper.createRequestMetadata(request);

        TodoSuccessResponse response = new TodoSuccessResponse();
        response.setMetadata(metadata);
        response.setTodo(todoFetched);
        response.getMetadata().setStatus(todoResponseHelper.createSuccessStatus());

        todoResponseHelper.updateMetadata(response.getMetadata());

        return ResponseEntity.ok(response);
    }

    @Override
    @RequestMapping(
            value = "/{todoId}",
            method = RequestMethod.DELETE,
            produces = {APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<TodoResponse> deleteTodo(
            @PathVariable("todoId")
                    String todoId,
            HttpServletRequest request) {

        // Convert the String to a Long.
        Todo todoFetched = todoService.deleteTodo(todoId);

        Metadata metadata = todoResponseHelper.createRequestMetadata(request);

        TodoSuccessResponse response = new TodoSuccessResponse();
        response.setMetadata(metadata);
        response.setTodo(todoFetched);
        response.getMetadata().setStatus(todoResponseHelper.createSuccessStatus());

        todoResponseHelper.updateMetadata(response.getMetadata());

        return ResponseEntity.ok(response);
    }

    Status createSuccessStatus() {
        Status status = new Status();
        status.setSuccess(true);
        status.setStatusCode(HttpStatus.OK.getReasonPhrase());
        return status;
    }

    Metadata createRequestMetadata(HttpServletRequest request) {
        Metadata metadata = new Metadata();
        metadata.setRequestId(UUID.randomUUID().toString());
        metadata.setRequestTimestamp("" + Instant.now().toEpochMilli());
        metadata.setServiceName("Todo Service");
        metadata.setServiceVersion("1.0");
        metadata.setResponseId(UUID.randomUUID().toString());
        metadata.setResponseTimestamp("" + Instant.now().toEpochMilli());
        return metadata;
    }

    @ExceptionHandler({TodoNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<TodoResponse> todoNotFound(
            HttpServletRequest request, RuntimeException e) {

        TodoErrorResponse response = new TodoErrorResponse();

        Metadata metadata = todoResponseHelper.createRequestMetadata(request);
        response.setMetadata(metadata);
        response.getMetadata().setStatus(
                todoResponseHelper.createFailureStatus(
                        e.getMessage(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()));

        Issue issue = new Issue();
        issue.setIssueMessage(e.getMessage());
        issue.setIssueCode(HttpStatus.NOT_FOUND.toString());
        response.setIssues(Collections.singletonList(issue));

        todoResponseHelper.updateMetadata(response.getMetadata());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidIdException.class, InvalidAssigneeException.class})
    private ResponseEntity<TodoResponse> todoPreconditionFailed(
            HttpServletRequest request, RuntimeException e) {

        TodoErrorResponse response = new TodoErrorResponse();

        Metadata metadata = todoResponseHelper.createRequestMetadata(request);
        response.setMetadata(metadata);
        response.getMetadata().setStatus(
                todoResponseHelper.createFailureStatus(
                        e.getMessage(),
                        HttpStatus.PRECONDITION_FAILED.getReasonPhrase()));

        Issue issue = new Issue();
        issue.setIssueMessage(e.getMessage());
        issue.setIssueCode(HttpStatus.PRECONDITION_FAILED.toString());
        response.setIssues(Collections.singletonList(issue));

        todoResponseHelper.updateMetadata(response.getMetadata());

        return new ResponseEntity<>(response, HttpStatus.PRECONDITION_FAILED);
    }
}