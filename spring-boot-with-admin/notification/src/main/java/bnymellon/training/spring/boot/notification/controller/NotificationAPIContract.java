/*
 * Copyright 2022 The Bank of New York Mellon.
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

package bnymellon.training.spring.boot.notification.controller;

import bnymellon.training.spring.boot.notification.model.Todo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

import java.util.List;

@RequestMapping("/notifications")
public interface NotificationAPIContract {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    ResponseEntity<Flux<List<Todo>>> getNotifications();

    @GetMapping(value = "/{dueDay}",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    ResponseEntity<Flux<List<Todo>>> getNotificationsByDueDay(@PathVariable("dueDay") String dueDay);

    @GetMapping(
            value = "/assignees/{assignee}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.ALL_VALUE},
            produces = {MediaType.TEXT_EVENT_STREAM_VALUE}
    )
    ResponseEntity<Flux<List<Todo>>> getNotificationsByAssignee(@PathVariable("assignee") String assignee);
}
