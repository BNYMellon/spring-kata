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

import bnymellon.training.spring.boot.todo.AbstractTodoMockSetupTest;
import bnymellon.training.spring.boot.todo.model.Todo;
import bnymellon.training.spring.boot.todo.model.exception.InvalidAssigneeException;
import bnymellon.training.spring.boot.todo.model.exception.InvalidIdException;
import bnymellon.training.spring.boot.todo.model.exception.TodoNotFoundException;
import bnymellon.training.spring.boot.todo.model.response.TodoCollectionResponse;
import bnymellon.training.spring.boot.todo.model.response.TodoSuccessResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class TodoAPIContractTest extends AbstractTodoMockSetupTest {

    private TodoAPIContract todoAPIContract;

    @BeforeEach
    public void setUp() {

        super.setUp();
        todoAPIContract = new TodoAPIContractImpl(todoService);
    }

    @Test
    public void addTodo() throws Exception {

        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setName("MyTodo 1");
        todo1.setActiveFlag(true);
        todo1.setAssignee("ABC1234");
        todo1.setDue("Today");
        todo1.setNotes("Some notes");

        TodoSuccessResponse savedTodo =
                (TodoSuccessResponse) todoAPIContract.addTodo(todo1, null).getBody();

        assertEquals(
                todo1,
                savedTodo.getTodo(),
                "The saved Todo should match [todo1]");
    }

    @Test
    public void getTodo() {

        TodoSuccessResponse getTodoResponse =
                (TodoSuccessResponse)
                        todoAPIContract.getTodo("2", null).getBody();

        assertEquals(
                todo2,
                getTodoResponse.getTodo(),
                "Todo2 should be fetched");
    }

    @Test
    public void getNonExistingTodo() {

        String todoId = null;

        assertThrows(
                InvalidIdException.class,
                () -> todoAPIContract.getTodo(todoId, null).getBody(),
                "Should throw an InvalidIdException");
    }

    @Test
    public void getAllTodos() {

        TodoCollectionResponse todos =
                (TodoCollectionResponse)
                        (todoAPIContract.getTodos(false, null).getBody());

        assertEquals(
                2,
                todos.getTodos().size(),
                "There should be a total of 2 Todos");
    }

    @Test
    public void getAllActiveTodos() {

        TodoCollectionResponse todos =
                (TodoCollectionResponse)
                        (todoAPIContract.getTodos(false, null).getBody());

        assertEquals(
                2,
                todos.getTodos().size(),
                "There should be a total of 2 active Todos");
    }

    @Test
    public void getTodosByAssignee() {

        TodoCollectionResponse todosCollection =
                (TodoCollectionResponse)
                        (todoAPIContract
                                .getTodosByAssignee("ABC1234", null)
                                .getBody());

        List<Todo> fetchedTodos = todosCollection.getTodos();

        assertEquals(1, fetchedTodos.size());

        Todo firstTodo = fetchedTodos.get(0);

        assertEquals(
                "MyTodo 1",
                firstTodo.getName(),
                "The todo fetched should have a name of: MyTodo 1");
    }

    @Test
    public void getNonExistingAssignee() {

        String assignee = null;


        assertThrows(InvalidAssigneeException.class, () -> {
                    todoAPIContract.getTodosByAssignee(assignee, null);
                },
                "Should throw an InvalidAssigneeException");
    }

    @Test
    public void getSmallValueForAssignee() {

        String assignee = "ABC";

        assertThrows(InvalidAssigneeException.class, () -> {
                    todoAPIContract.getTodosByAssignee(assignee, null);
                },
                "Should throw an InvalidAssigneeException");
    }

    @Test
    public void getLargeValueForAssignee() {

        String assignee = "ABCDEFGHIJKL";

        assertThrows(InvalidAssigneeException.class, () -> {
                    todoAPIContract.getTodosByAssignee(assignee, null);
                },
                "Should throw an InvalidAssigneeException");
    }

    @Test
    public void updateTodo() {

        todo1.setName("MyTodo 8");
        todo1.setActiveFlag(true);
        todo1.setAssignee("ABC1234");
        todo1.setDue("Tomorrow");
        todo1.setNotes("Some notes");

        Todo savedTodo =
                ((TodoSuccessResponse)
                        todoAPIContract.updateTodo("1", todo1, null)
                                .getBody())
                        .getTodo();

        assertEquals(
                "Tomorrow",
                savedTodo.getDue(),
                "The update should alter the Due date from ");
    }

    @Test
    public void updateNonExistentTodo() {

        Todo todo7 = new Todo();
        todo7.setName("MyTodo 8");
        todo7.setActiveFlag(true);
        todo7.setAssignee("ABC1234");
        todo7.setDue("Tomorrow");
        todo7.setNotes("Some notes");

        assertThrows(TodoNotFoundException.class, () -> {
                    todoAPIContract.updateTodo("7", todo7, null);
                },
                "Should throw a TodoNotFoundException");
    }

    @Test
    public void deleteTodo() {

        TodoSuccessResponse deleteTodoResponse =
                ((TodoSuccessResponse)
                        todoAPIContract
                                .deleteTodo("1", null).getBody());

        Todo deletedTodo = deleteTodoResponse.getTodo();

        assertFalse(
                deletedTodo.getActiveFlag(),
                "The deletion should set the activeFlag to false");
    }

    @Test
    public void deleteNonExistentTodo() {

        assertThrows(TodoNotFoundException.class, () -> {
                    todoAPIContract.deleteTodo("7", null);
                },
                "Should throw a TodoNotFoundException");
    }
}