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

package bny.training.spring.boot.todo.controller;


import bny.training.spring.boot.todo.model.response.Metadata;
import bny.training.spring.boot.todo.model.response.Status;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.UUID;

public class TodoResponseHelper {

    // Helper methods.
    Status createSuccessStatus() {
        Status status = new Status();
        status.setSuccess(true);
        status.setStatusCode(HttpStatus.OK.getReasonPhrase());
        return status;
    }

    Status createFailureStatus(String message, String statusCode) {
        Status status = new Status();
        status.setSuccess(false);
        status.setStatusCode(statusCode);
        status.setStatusDescription(message);
        return status;
    }

    void updateMetadata(Metadata metadata) {
    }

    Metadata createRequestMetadata(HttpServletRequest request) {
        Metadata metadata = new Metadata();
        metadata.setRequestId(UUID.randomUUID().toString());
        metadata.setRequestTimestamp("" + Instant.now().toEpochMilli());
        metadata.setServiceName("Todo Service");
        metadata.setServiceVersion("1.0");
        metadata.setResponseId(UUID.randomUUID().toString());
        metadata.setResponseTimestamp("" + Instant.now().toEpochMilli());
        return metadata;
    }

}
