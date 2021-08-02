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

import bnymellon.training.spring.boot.todo.model.Todo;
import bnymellon.training.spring.boot.todo.model.exception.InvalidAssigneeException;
import bnymellon.training.spring.boot.todo.model.exception.InvalidIdException;
import bnymellon.training.spring.boot.todo.model.exception.TodoNotFoundException;
import bnymellon.training.spring.boot.todo.service.TodoService;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static bnymellon.training.spring.boot.todo.TodoConstants.INVALID_ASSIGNEE_LENGTH_MESSAGE;
import static bnymellon.training.spring.boot.todo.TodoConstants.INVALID_ID_MESSAGE;
import static bnymellon.training.spring.boot.todo.TodoConstants.INVALID_NULL_ASSIGNEE_MESSAGE;
import static bnymellon.training.spring.boot.todo.TodoConstants.TODO_NOT_FOUND_EXCEPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TodoAPIContractImpl.class)
public class TodoAPIContractRestTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String STATUS_CODE_JSON_PATH = "metadata.status.statusCode";
    private static final String ISSUE_CODE_JSON_PATH = "issues[0].issueCode";
    private static final String ISSUE_MESSAGE_JSON_PATH = "issues[0].issueMessage";
    private static final String TODO_ID_JSON_PATH = "todo.id";
    private static final String TODO_NAME_JSON_PATH = "todo.name";
    private static final String TODO_ACTIVEFLAG_JSON_PATH = "todo.activeFlag";
    private static final String TODOS_JSON_PATH = "todos";
    private static final String FIRST_TODO_ID_JSON_PATH = "todos[0].id";

    @MockBean
    private TodoService todoService;

    private String todo1String;
    private String todo1Update;

    private Todo todo1;
    private Todo todo2;
    private Todo todo3;
    private Todo updatedTodo;
    private Todo deletedTodo;


    @BeforeEach
    public void setUp() {
        todo1 = new Todo();
        //This is not needed, but added so we can test equals() later.
        todo1.setId(1L);
        todo1.setName("MyTodo 1");
        todo1.setActiveFlag(true);
        todo1.setAssignee("ABC1234");
        todo1.setDue("Today");
        todo1.setNotes("Some notes");

        todo2 = new Todo();
        //This is not needed, but added so we can test equals() later.
        todo2.setId(2L);
        todo2.setName("MyTodo 2");
        todo2.setActiveFlag(true);
        todo2.setAssignee("XYZ9876");
        todo2.setDue("Soon");
        todo2.setNotes("Some notes for Todo 2");


        todo3 = new Todo();
        //This is not needed, but added so we can test equals() later.
        todo3.setId(3L);
        todo3.setName("MyTodo 1");
        todo3.setActiveFlag(true);
        todo3.setAssignee("ABC1234");
        todo3.setDue("Today");
        todo3.setNotes("Some notes");

        updatedTodo = new Todo();
        updatedTodo.setId(1L);
        updatedTodo.setName("Updated MyTodo 1");
        updatedTodo.setAssignee("ABC1234");
        updatedTodo.setDue("Today");
        updatedTodo.setNotes("Some notes");
        updatedTodo.setActiveFlag(true);

        deletedTodo = new Todo();
        deletedTodo.setId(1L);
        deletedTodo.setName("MyTodo 1");
        deletedTodo.setAssignee("ABC1234");
        deletedTodo.setDue("Today");
        deletedTodo.setNotes("Some notes");
        deletedTodo.setActiveFlag(false);

        todo1String = "{\"name\": \"MyTodo 1\"," +
                "\"assignee\": \"ABC1234\",\"due\": \"Today\"," +
                "\"notes\": \"Some notes\",\"activeFlag\": true}";

        todo1Update = "{\"id\": 1,\"name\": \"Updated MyTodo 1\"," +
                "\"assignee\": \"ABC1234\",\"due\": \"Today\"," +
                "\"notes\": \"Some notes\",\"activeFlag\": true}";

    }

    @Test
    /*
    The record to add is a JSON payload that looks like:

        {
            "name": "MyTodo 1",
            "assignee": "ABC1234",
            "due": "Today",
            "notes": "Some notes",
            "activeFlag": true
        }

    */
    public void addTodo() throws Exception {

        when(todoService.saveTodo(any(Todo.class)))
                .thenReturn(todo1);

        // Invoke the POST operation at the /todos endpoint and
        // supply a payload JSON of todo1String.
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .post("/todos")
                        .accept(MediaType.APPLICATION_JSON).content(todo1String)
                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        String jsonString = response.getContentAsString();

        assertEquals(
                HttpStatus.CREATED.value(),
                response.getStatus(),
                "The HTTP Status should be a 201");

        assertEquals(
                HttpStatus.CREATED.getReasonPhrase(),
                JsonPath.with(jsonString).getString(STATUS_CODE_JSON_PATH),
                "The Status Code should match the expected");

        assertEquals(
                1L,
                JsonPath.with(jsonString).getLong(TODO_ID_JSON_PATH),
                "The TODO ID should be 1");

        assertEquals(
                "MyTodo 1",
                JsonPath.with(jsonString).getString(TODO_NAME_JSON_PATH),
                "The TODO Name should be [MyTodo 1]");
    }

    @Test
    /*
    An attempt to fetch the todoId of 1 should result in a HTTP 200 OK with response, similar to:

        {
            "metadata": {
                "serviceName": "Todo Service",
                "serviceVersion": "1.0",
                "status": {
                    "success": true,
                    "statusCode": "OK"
                },
                "requestId": "f24f2cf9-d044-4c0c-9f79-f40707c129ac",
                "requestTimestamp": "1499397004493",
                "responseId": "6a023dbd-8990-44fe-b2bd-93a49c3fceaf",
                "responseTimestamp": "1499397004493"
            },
            "todo": {
                "id": 1,
                "name": "MyTodo 1",
                "assignee": "ABC1234",
                "due": "Today",
                "notes": "Some notes",
                "activeFlag": true
            }
        }

    */
    public void getTodo() throws Exception {

        when(todoService.getTodo(eq("1")))
                .thenReturn(todo1);

        // Invoke the GET operation at the /todos/{todoId} endpoint with a todoId of 1.
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .get("/todos/1")
                        .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        String jsonString = response.getContentAsString();

        assertEquals(
                HttpStatus.OK.value(),
                response.getStatus(),
                "The HTTP Status should be a 200");

        assertEquals(
                HttpStatus.OK.getReasonPhrase(),
                JsonPath.with(jsonString).getString(STATUS_CODE_JSON_PATH),
                "The Status Code should match the expected");

        assertEquals(
                1L,
                JsonPath.with(jsonString).getLong(TODO_ID_JSON_PATH),
                "The TODO ID should be 1");

        assertEquals(
                "MyTodo 1",
                JsonPath.with(jsonString).getString(TODO_NAME_JSON_PATH),
                "The TODO Name should be [MyTodo 1]");
    }

    @Test
    /*
        {
            "metadata": {
                "serviceName": "Todo Service",
                "serviceVersion": "1.0",
                "status": {
                    "success": false,
                    "statusCode": "Precondition Failed",
                    "statusDescription": "Invalid id input [null]"
                },
                "requestId": "0c270cf9-b17d-49bc-a5d5-398d54c55e28",
                "requestTimestamp": "1499397491467",
                "responseId": "bef6e2c5-bb00-479f-8a79-ef88916adb6d",
                "responseTimestamp": "1499397491467"
            },
            "issues": [
                {
                    "issueCode": "412",
                    "issueMessage": "Invalid id input [null]"
                }
            ]
        }

    */
    public void getNonExistingTodo() throws Exception {

        when(todoService.getTodo(eq("x")))
                .thenThrow(
                        new InvalidIdException(INVALID_ID_MESSAGE, "x"));

        // Invoke the GET operation at the /todos/{todoId} endpoint with a todoId of x.
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .get("/todos/x")
                        .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        String jsonString = response.getContentAsString();

        System.out.println(jsonString);

        assertEquals(
                HttpStatus.PRECONDITION_FAILED.value(),
                response.getStatus(),
                "The HTTP Status should be a 412");

        assertEquals(
                HttpStatus.PRECONDITION_FAILED.getReasonPhrase(),
                JsonPath.with(jsonString).getString(STATUS_CODE_JSON_PATH),
                "The Status Code should match the expected");

        assertEquals(
                "412 PRECONDITION_FAILED",
                JsonPath.with(jsonString).getString(ISSUE_CODE_JSON_PATH),
                "The Issue Code should be [412 PRECONDITION_FAILED]");

        assertEquals(
                String.format(INVALID_ID_MESSAGE, "x"),
                JsonPath.with(jsonString).getString(ISSUE_MESSAGE_JSON_PATH),
                "The Issue Message should be [Invalid id input [x]]");
    }

    @Test
    public void getAllTodos() throws Exception {

        when(todoService.getAllTodos(any(Boolean.class)))
                .thenReturn(Arrays.asList(todo1, todo2));

        // Invoke the GET operation at the /todos endpoint.
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .get("/todos")
                        .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        String jsonString = response.getContentAsString();

        System.out.println(jsonString);

        assertEquals(
                HttpStatus.OK.value(),
                response.getStatus(),
                "The HTTP Status should be a 200");

        assertEquals(
                2,
                JsonPath.with(jsonString).getList(TODOS_JSON_PATH).size(),
                "There should be two todo instances in the returned list");

        assertEquals(
                HttpStatus.OK.getReasonPhrase(),
                JsonPath.with(jsonString).getString(STATUS_CODE_JSON_PATH),
                "The Status Code should match the expected");
    }

    @Test
    public void getAllActiveTodos() throws Exception {

        when(todoService.getAllTodos(eq(Boolean.FALSE)))
                .thenReturn(Arrays.asList(todo1, todo2));

        // Invoke the GET operation at the /todos?includeInactive=false endpoint.
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .get("/todos?includeInactive=false")
                        .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        String jsonString = response.getContentAsString();

        assertEquals(
                HttpStatus.OK.value(),
                response.getStatus(),
                "The HTTP Status should be a 200");

        assertEquals(
                2,
                JsonPath.with(jsonString).getList(TODOS_JSON_PATH).size(),
                "There should be two todo instances in the returned list");

        assertEquals(
                HttpStatus.OK.getReasonPhrase(),
                JsonPath.with(jsonString).getString(STATUS_CODE_JSON_PATH),
                "The Status Code should match the expected");
    }

    @Test
    public void getTodosByAssignee() throws Exception {

        when(todoService.getTodosByAssignee(eq("ABC1234")))
                .thenReturn(Arrays.asList(todo1));

        // Invoke the GET operation at the /todos/assignees/{assignee} endpoint with a ABC1234.
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .get("/todos/assignees/ABC1234")
                        .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        String jsonString = response.getContentAsString();

        System.out.println(jsonString);

        assertEquals(
                HttpStatus.OK.value(),
                response.getStatus(),
                "The HTTP Status should be a 200");

        assertEquals(
                1,
                JsonPath.with(jsonString).getList(TODOS_JSON_PATH).size(),
                "There should be one todo instance in the returned list");

        assertEquals(
                HttpStatus.OK.getReasonPhrase(),
                JsonPath.with(jsonString).getString(STATUS_CODE_JSON_PATH),
                "The Status Code should match the expected");

        assertEquals(
                1L,
                JsonPath.with(jsonString).getLong(FIRST_TODO_ID_JSON_PATH),
                "The ID should match");
    }

    @Test
    public void getNonExistingAssignee() throws Exception {

        when(todoService.getTodosByAssignee(eq("XYZ9999")))
                .thenThrow(new InvalidAssigneeException(INVALID_NULL_ASSIGNEE_MESSAGE, "XYZ9999"));

        // Invoke the GET operation at the /todos/assignees/{assignee} endpoint with XYZ9999.
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .get("/todos/assignees/XYZ9999")
                        .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        String jsonString = response.getContentAsString();

        System.out.println(jsonString);

        assertEquals(
                HttpStatus.PRECONDITION_FAILED.value(),
                response.getStatus(),
                "The HTTP Status should be a 412");

        assertEquals(
                HttpStatus.PRECONDITION_FAILED.getReasonPhrase(),
                JsonPath.with(jsonString).getString(STATUS_CODE_JSON_PATH),
                "The Status Code should match the expected");

        assertEquals(
                "412 PRECONDITION_FAILED",
                JsonPath.with(jsonString).getString(ISSUE_CODE_JSON_PATH),
                "The Issue Code should match");

        assertEquals(
                String.format(INVALID_NULL_ASSIGNEE_MESSAGE, "XYZ9999"),
                JsonPath.with(jsonString).getString(ISSUE_MESSAGE_JSON_PATH),
                "The Issue Message should match");
    }

    @Test
    public void getSmallValueForAssignee() throws Exception {

        when(todoService.getTodosByAssignee(eq("ABC")))
                .thenThrow(new InvalidAssigneeException(INVALID_ASSIGNEE_LENGTH_MESSAGE, "ABC"));

        // Invoke the GET operation at the /todos/assignees/{assignee} endpoint with ABC.
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .get("/todos/assignees/ABC")
                        .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        String jsonString = response.getContentAsString();

        assertEquals(
                HttpStatus.PRECONDITION_FAILED.value(),
                response.getStatus(),
                "The HTTP Status should be a 412");

        assertEquals(
                HttpStatus.PRECONDITION_FAILED.getReasonPhrase(),
                JsonPath.with(jsonString).getString(STATUS_CODE_JSON_PATH),
                "The Status Code should match the expected");

        assertEquals(
                "412 PRECONDITION_FAILED",
                JsonPath.with(jsonString).getString(ISSUE_CODE_JSON_PATH),
                "The Issue Code should match");

        assertEquals(
                String.format(INVALID_ASSIGNEE_LENGTH_MESSAGE, "ABC"),
                JsonPath.with(jsonString).getString(ISSUE_MESSAGE_JSON_PATH),
                "The Issue Message should match");
    }

    @Test
    public void getLargeValueForAssignee() throws Exception {

        when(todoService.getTodosByAssignee(eq("ABCDEFGHIJKL")))
                .thenThrow(new InvalidAssigneeException(
                                INVALID_ASSIGNEE_LENGTH_MESSAGE, "ABCDEFGHIJKL"));

        // Invoke the GET operation at the /todos/assignees/{assignee} endpoint with ABCDEFGHIJKL.
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .get("/todos/assignees/ABCDEFGHIJKL")
                        .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        String jsonString = response.getContentAsString();

        assertEquals(
                HttpStatus.PRECONDITION_FAILED.value(),
                response.getStatus(),
                "The HTTP Status should be a 412");

        assertEquals(
                HttpStatus.PRECONDITION_FAILED.getReasonPhrase(),
                JsonPath.with(jsonString).getString(STATUS_CODE_JSON_PATH),
                "The Status Code should match the expected");

        assertEquals(
                "412 PRECONDITION_FAILED",
                JsonPath.with(jsonString).getString(ISSUE_CODE_JSON_PATH),
                "The Issue Code should match");

        assertEquals(
                String.format(INVALID_ASSIGNEE_LENGTH_MESSAGE, "ABCDEFGHIJKL"),
                JsonPath.with(jsonString).getString(ISSUE_MESSAGE_JSON_PATH),
                "The Issue Message should match");
    }

    @Test
    public void updateTodo() throws Exception {

        when(todoService.updateTodo(eq("1"), any(Todo.class)))
                .thenReturn(updatedTodo);

        // Invoke the PUT operation at the /todos/{todoId} endpoint and
        // supply a payload JSON of todo1String.
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .put("/todos/1")
                        .accept(MediaType.APPLICATION_JSON).content(todo1Update)
                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        String jsonString = response.getContentAsString();

        assertEquals(
                HttpStatus.OK.value(),
                response.getStatus(),
                "The HTTP Status should be a 200");

        assertEquals(
                HttpStatus.OK.getReasonPhrase(),
                JsonPath.with(jsonString).getString(STATUS_CODE_JSON_PATH),
                "The Status Code should match the expected");

        assertEquals(
                1L,
                JsonPath.with(jsonString).getLong(TODO_ID_JSON_PATH),
                "The TODO ID should be 1");

        assertEquals(
                "Updated MyTodo 1",
                JsonPath.with(jsonString).getString(TODO_NAME_JSON_PATH),
                "The TODO Name should be [Updated MyTodo 1]");
    }

    @Test
    public void updateNonExistentTodo() throws Exception {

        when(todoService.updateTodo(eq("7"), any(Todo.class)))
                .thenThrow(new TodoNotFoundException(TODO_NOT_FOUND_EXCEPTION, 7L));

        // Invoke the PUT operation at the /todos/{todoId} endpoint and
        // supply a payload JSON of todo1String.
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .put("/todos/7")
                        .accept(MediaType.APPLICATION_JSON).content(todo1Update)
                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        String jsonString = response.getContentAsString();

        assertEquals(
                HttpStatus.NOT_FOUND.value(),
                response.getStatus(),
                "The HTTP Status should be a 404");

        assertEquals(
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                JsonPath.with(jsonString).getString(STATUS_CODE_JSON_PATH),
                "The Status Code should match the expected");

        assertEquals(
                "404 NOT_FOUND",
                JsonPath.with(jsonString).getString(ISSUE_CODE_JSON_PATH),
                "The Issue Code should be [404 NOT_FOUND]");

        assertEquals(
                String.format(TODO_NOT_FOUND_EXCEPTION, "7"),
                JsonPath.with(jsonString).getString(ISSUE_MESSAGE_JSON_PATH),
                "The Issue Message should be [A Todo with id [7] was not found.]");
    }

    @Test
    public void deleteTodo() throws Exception {

        when(todoService.deleteTodo(eq("1")))
                .thenReturn(deletedTodo);

        // Invoke the PUT operation at the /todos/{todoId} endpoint and
        // supply a payload JSON of todo1String.
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .delete("/todos/1")
                        .accept(MediaType.APPLICATION_JSON).content(todo1Update)
                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        String jsonString = response.getContentAsString();

        assertEquals(
                HttpStatus.OK.value(),
                response.getStatus(),
                "The HTTP Status should be a 200");

        assertEquals(
                HttpStatus.OK.getReasonPhrase(),
                JsonPath.with(jsonString).getString(STATUS_CODE_JSON_PATH),
                "The Status Code should match the expected");

        assertEquals(
                1L,
                JsonPath.with(jsonString).getLong(TODO_ID_JSON_PATH),
                "The TODO ID should be 1");

        assertEquals(
                Boolean.FALSE,
                JsonPath.with(jsonString).getBoolean(TODO_ACTIVEFLAG_JSON_PATH),
                "The TODO Active Flag should be false");
/*

        given()
                .port(port)
                .when()
                .contentType("application/json\r\n")
                .delete("/todos/{todoId}", "1")
                .then()
                .statusCode(200)
                .body("metadata.status.success", equalTo(Boolean.TRUE),
                        "metadata.status.statusCode", equalTo(HttpStatus.OK.getReasonPhrase()),
                        "todo.activeFlag", equalTo(Boolean.FALSE));
*/
    }

    @Test
    public void deleteNonExistentTodo() throws Exception {

        when(todoService.deleteTodo(eq("7")))
                .thenThrow(new TodoNotFoundException(TODO_NOT_FOUND_EXCEPTION, 7L));

        // Invoke the PUT operation at the /todos/{todoId} endpoint and
        // supply a payload JSON of todo1String.
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .delete("/todos/7")
                        .accept(MediaType.APPLICATION_JSON).content(todo1Update)
                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        String jsonString = response.getContentAsString();

        assertEquals(
                HttpStatus.NOT_FOUND.value(),
                response.getStatus(),
                "The HTTP Status should be a 404");

        assertEquals(
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                JsonPath.with(jsonString).getString(STATUS_CODE_JSON_PATH),
                "The Status Code should match the expected");

        assertEquals(
                "404 NOT_FOUND",
                JsonPath.with(jsonString).getString(ISSUE_CODE_JSON_PATH),
                "The Issue Code should be [404 NOT_FOUND]");

        assertEquals(
                String.format(TODO_NOT_FOUND_EXCEPTION, "7"),
                JsonPath.with(jsonString).getString(ISSUE_MESSAGE_JSON_PATH),
                "The Issue Message should be [A Todo with id [7] was not found.]");
/*

        String errorMessage = String.format(TodoConstants.TODO_NOT_FOUND_EXCEPTION, "7");

        given()
                .port(port)
                .when()
                .contentType("application/json\r\n")
                .delete("/todos/{todoId}", "7")
                .then()
                .statusCode(404)
                .body("metadata.status.success", equalTo(Boolean.FALSE),
                        "metadata.status.statusCode", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase()),
                        "metadata.status.statusDescription", equalTo(errorMessage),
                        "issues[0].issueCode", equalTo(HttpStatus.NOT_FOUND.toString()),
                        "issues[0].issueMessage", equalTo(errorMessage));
*/
    }
}