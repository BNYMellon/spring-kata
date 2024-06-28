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

package bny.training.spring.boot.todo.model.response;

import bny.training.spring.boot.todo.model.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class TodoSuccessResponseTest {
    private TodoSuccessResponse todoServiceResponse1;
    private TodoSuccessResponse todoServiceResponse2;
    private TodoSuccessResponse todoServiceResponse3;
    private TodoSuccessResponse todoServiceResponse4;

    @BeforeEach
    public void setUp() throws Exception {
        Todo todo1 = new Todo();
        todo1.setId(3L);
        todo1.setName("MyTodo 8");
        todo1.setActiveFlag(true);
        todo1.setAssignee("ABC1234");
        todo1.setDue("Today");
        todo1.setNotes("Some notes");

        Issue issue1 = new Issue();
        issue1.setIssueCode("IssueCode");
        issue1.setIssueMessage("IssueMessage");

        Metadata metadata1 = new Metadata();
        metadata1.setDescription("Description");
        metadata1.setRequestId("requestId");
        metadata1.setRequestTimestamp("requestTimestamp");
        metadata1.setResponseId("responseId");
        metadata1.setResponseTimestamp("responseTimestamp");
        metadata1.setServiceName("serviceName");
        metadata1.setServiceVersion("serviceVersion");
        metadata1.setStatus(null);

        todoServiceResponse1 = new TodoSuccessResponse();
        todoServiceResponse1.setTodo(todo1);

        todoServiceResponse2 = new TodoSuccessResponse();
        todoServiceResponse2.setTodo(todo1);

        todoServiceResponse3 = new TodoSuccessResponse();
        todoServiceResponse3.setMetadata(metadata1);

        todoServiceResponse4 = new TodoSuccessResponse();
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals(
                todoServiceResponse1,
                todoServiceResponse2,
                "TodoServiceResponse1 and TodoServiceResponse2 should be equal");
        assertNotEquals(
                todoServiceResponse1,
                todoServiceResponse3,
                "TodoServiceResponse1 and TodoServiceResponse3 should not be equal");
        assertNotEquals(
                todoServiceResponse1,
                todoServiceResponse4,
                "TodoServiceResponse1 and TodoServiceResponse3 should not be equal");
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(
                todoServiceResponse1.hashCode(),
                todoServiceResponse2.hashCode(),
                "TodoServiceResponse1 and TodoServiceResponse2 should have the same " +
                        "hashCode");
        assertNotEquals(
                todoServiceResponse1.hashCode(),
                todoServiceResponse3.hashCode(),
                "TodoServiceResponse1 and TodoServiceResponse3 should not have the same " +
                        "hashCode");
        assertNotEquals(
                todoServiceResponse1.hashCode(),
                todoServiceResponse4.hashCode(),
                "TodoServiceResponse1 and TodoServiceResponse3 should not have the same " +
                        "hashCode");
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(
                todoServiceResponse1.toString(),
                todoServiceResponse2.toString(),
                "TodoServiceResponse1 and TodoServiceResponse2 should have the same toString");
        assertNotEquals(
                todoServiceResponse1.toString(),
                todoServiceResponse3.toString(),
                "TodoServiceResponse1 and TodoServiceResponse3 should not have the same " +
                        "toString");
        assertNotEquals(
                todoServiceResponse1.toString(),
                todoServiceResponse4.toString(),
                "TodoServiceResponse1 and TodoServiceResponse3 should not have the same " +
                        "toString");
    }

}