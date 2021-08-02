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

package bnymellon.training.spring.boot.todo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoTest {

    Todo emptyTodo1;
    Todo emptyTodo2;
    private Todo todo1;
    private Todo todo2;

    @BeforeEach
    public void setUp() throws Exception {

        emptyTodo1 = new Todo();
        emptyTodo2 = new Todo();

        todo1 = new Todo();
        todo1.setId(1L);
        todo1.setName("MyTodo");
        todo1.setActiveFlag(true);
        todo1.setAssignee("ME");
        todo1.setDue("Today");
        todo1.setNotes("Some notes");

        todo2 = new Todo();
        todo2.setId(1L);
        todo2.setName("MyTodo");
        todo2.setActiveFlag(true);
        todo2.setAssignee("ME");
        todo2.setDue("Today");
        todo2.setNotes("Some notes");
    }

    @Test
    public void testEmptyEquals() throws Exception {

        assertTrue(
                emptyTodo1.equals(emptyTodo2),
                "Both empty Todo instances should be equal");
    }

    @Test
    public void testContentEquals() throws Exception {

        assertTrue(
                todo1.equals(todo2),
                "Both non-empty Todo instances should be equal");
    }

    @Test
    public void testNotEquals() throws Exception {

        assertFalse(
                emptyTodo1.equals(todo2),
                "The Todo instances should not be equal");
    }

    @Test
    public void testEmptyHashCode() throws Exception {

        assertEquals(
                emptyTodo1.hashCode(),
                emptyTodo2.hashCode(),
                "Both empty Todo instances should have the same hashCode");
    }

    @Test
    public void testContentHashCode() throws Exception {

        assertEquals(
                todo1.hashCode(),
                todo2.hashCode(),
                "Both non-empty Todo instances should have the same hashCode");
    }

    @Test
    public void testHashCode() throws Exception {

        assertNotEquals(
                emptyTodo1.hashCode(),
                todo2.hashCode(),
                "The Todo instances should not have the same hashCode");
    }

    @Test
    public void testEmptyToString() throws Exception {

        assertEquals(
                emptyTodo1.toString(),
                emptyTodo2.toString(),
                "Both empty Todo instances should have the same toString");
    }

    @Test
    public void testContentToString() throws Exception {

        assertEquals(
                todo1.toString(),
                todo2.toString(),
                "Both non-empty Todo instances should have the same toString");
    }

    @Test
    public void testNotToString() throws Exception {

        assertNotEquals(
                emptyTodo1.toString(),
                todo2.toString(),
                "The Todo instances should not have the same toString");
    }
}