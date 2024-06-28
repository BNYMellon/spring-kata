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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MetadataTest {

    private Metadata metadata1;
    private Metadata metadata2;
    private Metadata metadata3;
    private Metadata metadata4;

    @BeforeEach
    public void setUp() throws Exception {

        metadata1 = new Metadata();
        metadata1.setDescription("Description");
        metadata1.setRequestId("requestId");
        metadata1.setRequestTimestamp("requestTimestamp");
        metadata1.setResponseId("responseId");
        metadata1.setResponseTimestamp("responseTimestamp");
        metadata1.setServiceName("serviceName");
        metadata1.setServiceVersion("serviceVersion");
        metadata1.setStatus(null);

        metadata2 = new Metadata();
        metadata2.setDescription("Description");
        metadata2.setRequestId("requestId");
        metadata2.setRequestTimestamp("requestTimestamp");
        metadata2.setResponseId("responseId");
        metadata2.setResponseTimestamp("responseTimestamp");
        metadata2.setServiceName("serviceName");
        metadata2.setServiceVersion("serviceVersion");
        metadata2.setStatus(null);

        metadata3 = new Metadata();
        metadata3.setDescription("Description3");
        metadata3.setRequestId("requestId3");
        metadata3.setRequestTimestamp("requestTimestamp3");
        metadata3.setResponseId("responseId3");
        metadata3.setResponseTimestamp("responseTimestamp3");
        metadata3.setServiceName("serviceName3");
        metadata3.setServiceVersion("serviceVersion3");
        metadata3.setStatus(null);

        metadata4 = new Metadata();
        metadata4.setDescription("Description3");
        metadata4.setRequestId("requestId3");
        metadata4.setRequestTimestamp(null);
        metadata4.setResponseId("responseId3");
        metadata4.setResponseTimestamp(null);
        metadata4.setServiceName("serviceName3");
        metadata4.setServiceVersion("serviceVersion3");
        metadata4.setStatus(null);
    }

    @Test
    public void testEquals() throws Exception {

        assertEquals(
                metadata1,
                metadata2,
                "Metadata1 and Metadata2 should be equal");

        assertNotEquals(
                metadata1,
                metadata3,
                "Metadata1 and Metadata3 should not be equal");

        assertNotEquals(
                metadata1,
                metadata4,
                "Metadata1 and Metadata3 should not be equal");
    }

    @Test
    public void testHashCode() throws Exception {

        assertEquals(
                metadata1.hashCode(),
                metadata2.hashCode(),
                "Metadata1 and Metadata2 should have the same hashCode");

        assertNotEquals(
                metadata1.hashCode(),
                metadata3.hashCode(),
                "Metadata1 and Metadata3 should not have the same hashCode");

        assertNotEquals(
                metadata1.hashCode(),
                metadata4.hashCode(),
                "Metadata1 and Metadata3 should not have the same hashCode");
    }

    @Test
    public void testToString() throws Exception {

        assertEquals(
                metadata1.toString(),
                metadata2.toString(),
                "Metadata1 and Metadata2 should have the same toString");

        assertNotEquals(
                metadata1.toString(),
                metadata3.toString(),
                "Metadata1 and Metadata3 should not have the same toString");

        assertNotEquals(
                metadata1.toString(),
                metadata4.toString(),
                "Metadata1 and Metadata3 should not have the same toString");
    }

}