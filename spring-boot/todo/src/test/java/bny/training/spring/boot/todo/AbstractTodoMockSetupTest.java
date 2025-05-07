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

package bny.training.spring.boot.todo;

import bny.training.spring.boot.todo.model.Todo;
import bny.training.spring.boot.todo.model.exception.InvalidAssigneeException;
import bny.training.spring.boot.todo.model.exception.InvalidIdException;
import bny.training.spring.boot.todo.model.exception.TodoNotFoundException;
import bny.training.spring.boot.todo.service.TodoService;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbstractTodoMockSetupTest {

    protected Todo todo1;
    protected Todo todo2;

    @MockBean
    protected TodoService todoService;

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

        // Mocking the dao layer.
        todoService = mock(TodoService.class);

        // Return the same todo as the one saved.
        when(todoService.saveTodo(eq(todo1)))
                .thenReturn(todo1);

        // Return the same todo as the one saved.
        when(todoService.saveTodo(eq(todo2)))
                .thenReturn(todo2);

        //  ------------ FIND BY ID ------------
        // Return a new todo of ID 1 when fetched.
//        when(todoService.getTodo(isNull()))
//                .thenThrow(new InvalidIdException(TodoConstants.INVALID_ID_MESSAGE, null));

        when(todoService.getTodo("1"))
                .thenReturn(todo1);

        // Return a new todo of ID 2 when fetched.
        when(todoService.getTodo("2"))
                .thenReturn(todo2);

        when(todoService.getTodo(null))
                .thenThrow(new InvalidIdException(TodoConstants.INVALID_ID_MESSAGE, null));

        when(todoService.getTodo("7"))
                .thenThrow(new TodoNotFoundException(TodoConstants.TODO_NOT_FOUND_EXCEPTION, 7L));


        //  ------------ FIND ------------
        // Return a list of todos when all fetched.
        when(todoService.getAllTodos(true))
                .thenReturn(Arrays.asList(new Todo[]{todo1, todo2}));

        // Return a list of todos when all active fetched.
        when(todoService.getAllTodos(false))
                .thenReturn(Arrays.asList(new Todo[]{todo1, todo2}));

        // Return a new todo of ID 1 when fetched for assignee ABC1234.
        when(todoService.getTodosByAssignee(eq("ABC1234")))
                .thenReturn(Arrays.asList(new Todo[]{todo1}));

        // Return a new todo of ID 2 when fetched for assignee XYZ9876.
        when(todoService.getTodosByAssignee(eq("XYZ9876")))
                .thenReturn(Arrays.asList(new Todo[]{todo2}));

        // Return a new todo of ID 1 when fetched for assignee ABC.
        when(todoService.getTodosByAssignee(eq("ABC")))
                .thenThrow(
                        new InvalidAssigneeException(
                                TodoConstants.INVALID_ASSIGNEE_LENGTH_MESSAGE, "ABC"));

        // Return a new todo of ID 1 when fetched for assignee ABCDEFGHIJKL.
        when(todoService.getTodosByAssignee(eq("ABCDEFGHIJKL")))
                .thenThrow(
                        new InvalidAssigneeException(
                                TodoConstants.INVALID_ASSIGNEE_LENGTH_MESSAGE, "ABCDEFGHIJKL"));

        // Return a new todo of ID 1 when fetched for assignee ABCDEFGHIJKL.
        when(todoService.getTodosByAssignee(isNull()))
                .thenThrow(
                        new InvalidAssigneeException(
                                TodoConstants.INVALID_NULL_ASSIGNEE_MESSAGE, null));


        //  ------------ UPDATE ------------
        when(todoService.updateTodo(isNull(), any(Todo.class)))
                .thenThrow(new InvalidIdException(TodoConstants.INVALID_ID_MESSAGE, null));

        when(todoService.updateTodo(eq("1"), any(Todo.class)))
                .thenAnswer(invocation -> invocation.getArguments()[1]);

        when(todoService.updateTodo(eq("2"), any(Todo.class)))
                .thenAnswer(invocation -> invocation.getArguments()[1]);

        when(todoService.updateTodo(eq("7"), any(Todo.class)))
                .thenThrow(new TodoNotFoundException(TodoConstants.TODO_NOT_FOUND_EXCEPTION, 7L));


        //  ------------ DELETE ------------
        when(todoService.deleteTodo(eq("1")))
                .thenAnswer(invocation -> {
                            Todo retVal = todoService.getTodo(
                                    (String) invocation.getArguments()[0]);
                            retVal.setActiveFlag(false);
                            return retVal;
                        }
                );

        when(todoService.deleteTodo(eq("2")))
                .thenAnswer(invocation -> {
                            Todo retVal = todoService.getTodo(
                                    (String) invocation.getArguments()[0]);
                            retVal.setActiveFlag(false);
                            return retVal;
                        }
                );

        when(todoService.deleteTodo(eq("7")))
                .thenThrow(new TodoNotFoundException(TodoConstants.TODO_NOT_FOUND_EXCEPTION, 7L));

    }
}
