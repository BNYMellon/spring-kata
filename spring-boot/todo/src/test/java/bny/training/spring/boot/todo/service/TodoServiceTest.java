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

package bny.training.spring.boot.todo.service;

import bny.training.spring.boot.todo.dao.TodoRepository;
import bny.training.spring.boot.todo.model.Todo;
import bny.training.spring.boot.todo.model.exception.InvalidAssigneeException;
import bny.training.spring.boot.todo.model.exception.InvalidIdException;
import bny.training.spring.boot.todo.model.exception.TodoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TodoServiceTest {

    private TodoServiceImpl todoService;

    private TodoRepository todoRepository;

    protected Todo todo1;
    protected Todo todo2;


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

        // Mocking the dao layer.
        todoRepository = mock(TodoRepository.class);

        // Return the same todo as the one saved.
        when(todoRepository.save(any(Todo.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        //  ------------ FIND BY ID ------------
        // Return a new todo of ID 1 when fetched.
        when(todoRepository.findById(1L))
                .thenReturn(Optional.of(todo1));

        // Return a new todo of ID 2 when fetched.
        when(todoRepository.findById(2L))
                .thenReturn(Optional.of(todo2));

        when(todoRepository.findById(7L))
                .thenReturn(Optional.ofNullable(null));

        // Return a list of todos when all fetched.
        when(todoRepository.findAll())
                .thenReturn(Arrays.asList(new Todo[]{todo1, todo2}));

        // Return a list of todos when all active fetched.
        when(todoRepository.findByActiveFlagTrue())
                .thenReturn(Arrays.asList(new Todo[]{todo1, todo2}));

        when(todoRepository.saveAndFlush(any(Todo.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        // Return a new todo of ID 1 when fetched for assignee ABC1234.
        when(todoRepository.findByAssignee(eq("ABC1234")))
                .thenReturn(Arrays.asList(new Todo[]{todo1}));

        // Return a new todo of ID 2 when fetched for assignee XYZ9876.
        when(todoRepository.findByAssignee(eq("XYZ9876")))
                .thenReturn(Arrays.asList(new Todo[]{todo2}));

        todoService = new TodoServiceImpl(todoRepository);
    }

    @Test
    public void saveTodo() {

        Todo todo1 = new Todo();
        todo1.setId(3L);
        todo1.setName("MyTodo 8");
        todo1.setActiveFlag(true);
        todo1.setAssignee("ABC1234");
        todo1.setDue("Today");
        todo1.setNotes("Some notes");
        Todo savedTodo = todoService.saveTodo(todo1);

        assertEquals(
                todo1,
                savedTodo,
                "The saved todo should match [todo1]");
    }

    @Test
    public void getTodo() {

        assertEquals(
                todo2,
                todoService.getTodo("2"),
                "Todo2 should be fetched");
    }

    @Test
    public void getNonExistingNullTodoId() {

        assertThrows(
                InvalidIdException.class,
                () -> todoService.getTodo(null),
                "Should throw an InvalidIdException");
    }

    @Test
    public void getNonExistingTodoById() {

        assertThrows(
                InvalidIdException.class,
                () -> todoService.getTodo(null),
                "Should throw an InvalidIdException");
    }

    @Test
    public void getAllTodos() {

        assertEquals(
                2,
                todoService.getAllTodos(false).size(),
                "There should be a total of 2 Todos");
    }

    @Test
    public void getAllActiveTodos() {

        assertEquals(
                2,
                todoService.getAllTodos(true).size(),
                "There should be a total of 2 active Todos");
    }

    @Test
    public void getTodosByAssignee() {

        List<Todo> fetchedTodos = todoService.getTodosByAssignee("ABC1234");

        assertEquals(
                1,
                fetchedTodos.size(),
                "There should be exactly one Todo for [ABC1234]");

        Todo firstTodo = fetchedTodos.get(0);
        assertEquals(
                "MyTodo 1",
                firstTodo.getName(),
                "The todo fetched should have a name of: MyTodo 1");
    }

    @Test
    public void getNonExistingAssignee() {

        assertThrows(InvalidAssigneeException.class, () -> {
                    todoService.getTodosByAssignee(null);
                },
                "Should throw an InvalidAssigneeException");
    }

    @Test
    public void getSmallValueForAssignee() {

        assertThrows(InvalidAssigneeException.class, () -> {
                    todoService.getTodosByAssignee("ABC");
                },
                "Should throw an InvalidAssigneeException");
    }

    @Test
    public void getLargeValueForAssignee() {

        assertThrows(InvalidAssigneeException.class, () -> {
                    todoService.getTodosByAssignee("ABCDEFGHIJKL");
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

        Todo savedTodo = todoService.updateTodo("1", todo1);

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
                    todoService.updateTodo("7", todo7);
                },
                "Should throw a TodoNotFoundException");
    }

    @Test
    public void deleteTodo() {

        Todo savedTodo = todoService.deleteTodo("1");

        assertFalse(
                savedTodo.getActiveFlag(),
                "The deletion should set the activeFlag to false");
    }

    @Test
    public void deleteNonExistentTodo() {

        assertThrows(TodoNotFoundException.class, () -> {
                    todoService.deleteTodo("7");
                },
                "Should throw a TodoNotFoundException");
    }
}