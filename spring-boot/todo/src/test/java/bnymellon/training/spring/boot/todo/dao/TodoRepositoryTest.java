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

package bnymellon.training.spring.boot.todo.dao;

import bnymellon.training.spring.boot.todo.TodoApplication;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TodoApplication.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(TodoRepositoryTest.DATASET)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = {TodoRepositoryTest.DATASET})
@DirtiesContext
public class TodoRepositoryTest {
    protected static final String DATASET = "classpath:datasets/todos.xml";

    @Autowired
    private TodoRepository repository;

    @Test
    public void findOne() {

        assertNotNull(
                repository.findById(3L).orElse(null),
                "There should be a Todo with id: 3.");
        assertNull(
                repository.findById(9L).orElse(null),
                "There should be no Todo with id: 9.");
    }

    @Test
    public void findByAssignee() {

        assertEquals(
                3,
                repository.findByAssignee("XXX1111").size(),
                "There should be three Todos for the assignee: XXX1111.");
    }

    @Test
    public void findAll() {

        assertEquals(
                7,
                repository.findAll().size(),
                "There should be seven total Todos.");
    }

    @Test
    public void findActive() {

        assertEquals(
                4,
                repository.findByActiveFlagTrue().size(),
                "There should be four active Todos");
    }

    @Test
    public void findActiveByAssignee() {

        assertEquals(
                1,
                repository.findByActiveFlagTrueAndAssignee("XXX1111").size(),
                "There should be one active Todo for the assignee: XXX1111");
    }

    @Test
    public void findCustomNotes() {

        assertEquals(
                1,
                repository.findCustomNotes("Fix").get().size(),
                "There should be one Todo with a note containing the word: Fix");
    }

    @Test
    public void compareCustomAndStandardNotes() {

        assertEquals(
                repository.findCustomNotes("Fix").get().size(),
                repository.findByNotesContaining("Fix").get().size(),
                "The number of Todos with a note containing the word: Fix " +
                        "should be the same with both custom and named query styles.");
    }
}