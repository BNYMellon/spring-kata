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

package bnymellon.training.spring.boot.todo.model.response;

import java.util.Collections;

import bnymellon.training.spring.boot.todo.model.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class TodoCollectionResponseTest {

    private TodoCollectionResponse todoCollectionResponse1;
    private TodoCollectionResponse todoCollectionResponse2;
    private TodoCollectionResponse todoCollectionResponse3;
    private TodoCollectionResponse todoCollectionResponse4;

    @BeforeEach
    public void setUp() throws Exception {

        Todo todo1 = new Todo();
        todo1.setId(3L);
        todo1.setName("MyTodo 8");
        todo1.setActiveFlag(true);
        todo1.setAssignee("ABC1234");
        todo1.setDue("Today");
        todo1.setNotes("Some notes");

        Metadata metadata1 = new Metadata();
        metadata1.setDescription("Description");
        metadata1.setRequestId("requestId");
        metadata1.setRequestTimestamp("requestTimestamp");
        metadata1.setResponseId("responseId");
        metadata1.setResponseTimestamp("responseTimestamp");
        metadata1.setServiceName("serviceName");
        metadata1.setServiceVersion("serviceVersion");
        metadata1.setStatus(null);

        todoCollectionResponse1 = new TodoCollectionResponse();
        todoCollectionResponse1.setTodos(Collections.singletonList(todo1));

        todoCollectionResponse2 = new TodoCollectionResponse();
        todoCollectionResponse2.setTodos(Collections.singletonList(todo1));

        todoCollectionResponse3 = new TodoCollectionResponse();
        todoCollectionResponse3.setMetadata(metadata1);

        todoCollectionResponse4 = new TodoCollectionResponse();
    }

    @Test
    public void testEquals() throws Exception {

        assertEquals(
                todoCollectionResponse1,
                todoCollectionResponse2,
                "TodoCollectionServiceResponse1 and TodoCollectionServiceResponse2 " +
                        "should be equal");

        assertNotEquals(
                todoCollectionResponse1,
                todoCollectionResponse3,
                "TodoCollectionServiceResponse1 and TodoCollectionServiceResponse3 " +
                        "should not be equal");

        assertNotEquals(
                todoCollectionResponse1,
                todoCollectionResponse4,
                "TodoCollectionServiceResponse1 and TodoCollectionServiceResponse3 " +
                        "should not be equal");
    }

    @Test
    public void testHashCode() throws Exception {

        assertEquals(
                todoCollectionResponse1.hashCode(),
                todoCollectionResponse2.hashCode(),
                "TodoCollectionServiceResponse1 and TodoCollectionServiceResponse2 should " +
                        "have the same hashCode");

        assertNotEquals(
                todoCollectionResponse1.hashCode(),
                todoCollectionResponse3.hashCode(),
                "TodoCollectionServiceResponse1 and TodoCollectionServiceResponse3 " +
                        "should not have the same hashCode");

        assertNotEquals(
                todoCollectionResponse1.hashCode(),
                todoCollectionResponse4.hashCode(),
                "TodoCollectionServiceResponse1 and TodoCollectionServiceResponse3 " +
                        "should not have the same hashCode");
    }

    @Test
    public void testToString() throws Exception {

        assertEquals(
                todoCollectionResponse1.toString(),
                todoCollectionResponse2.toString(),
                "TodoCollectionServiceResponse1 and TodoCollectionServiceResponse2 " +
                        "should have the same toString");

        assertNotEquals(
                todoCollectionResponse1.toString(),
                todoCollectionResponse3.toString(),
                "TodoCollectionServiceResponse1 and TodoCollectionServiceResponse3 " +
                        "should not have the same toString");

        assertNotEquals(
                todoCollectionResponse1.toString(),
                todoCollectionResponse4.toString(),
                "TodoCollectionServiceResponse1 and TodoCollectionServiceResponse3 " +
                        "should not have the same toString");
    }

}