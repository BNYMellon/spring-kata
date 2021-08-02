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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StatusTest {

    private Status status1;
    private Status status2;
    private Status status3;

    @BeforeEach
    public void setUp() throws Exception {

        status1 = new Status();
        status1.setSuccess(Boolean.TRUE);
        status1.setStatusCode("StatusCode");
        status1.setStatusDescription("StatusMessage");

        status2 = new Status();
        status2.setSuccess(Boolean.TRUE);
        status2.setStatusCode("StatusCode");
        status2.setStatusDescription("StatusMessage");

        status3 = new Status();
        status3.setSuccess(Boolean.FALSE);
        status3.setStatusCode("StatusCode3");
        status3.setStatusDescription("StatusMessage3");
    }

    @Test
    public void testEquals() throws Exception {

        assertEquals(
                status1,
                status2,
                "Status1 and Status2 should be equal");

        assertNotEquals(
                status1,
                status3,
                "Status1 and Status3 should not be equal");
    }

    @Test
    public void testHashCode() throws Exception {

        assertEquals(
                status1.hashCode(),
                status2.hashCode(),
                "Status1 and Status2 should have the same hashCode");

        assertNotEquals(
                status1.hashCode(),
                status3.hashCode(),
                "Status1 and Status3 should not have the same hashCode");
    }

    @Test
    public void testToString() throws Exception {

        assertEquals(
                status1.toString(),
                status2.toString(),
                "Status1 and Status2 should have the same toString");

        assertNotEquals("Status1 and Status3 should not have the same toString", status1.toString(), status3.toString());
    }
}