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
import bnymellon.training.spring.boot.notification.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class NotificationAPIContractTest {

    @MockBean
    private NotificationService service;

    @MockBean
    private WebClient.Builder webClientBuilder;

    @MockBean
    private WebClient webClient;

    private NotificationAPIContract apiContract;


    @BeforeEach
    void setUp() {
        this.service = Mockito.mock(NotificationService.class);
        this.webClientBuilder = Mockito.mock(WebClient.Builder.class);
        this.webClient = Mockito.mock(WebClient.class);

        when(this.webClientBuilder.uriBuilderFactory(any())).thenReturn(webClientBuilder);
        when(this.webClientBuilder.build()).thenReturn(webClient);

        this.apiContract = new NotificationAPIContractImpl(service);
    }

    @Test
    void getNotifications() {
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setName("MyTodo 1");
        todo1.setActiveFlag(true);
        todo1.setAssignee("ABC1234");
        todo1.setDue("Today");
        todo1.setNotes("Some notes");

        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setName("MyTodo 2");
        todo2.setActiveFlag(true);
        todo2.setAssignee("XYZ9876");
        todo2.setDue("Tomorrow");
        todo2.setNotes("Some notes for Todo 2");

        Flux<List<Todo>> todos = Flux.just(List.of(todo1, todo2));
        when(this.service.getAllNotifications()).thenReturn(todos);

        ResponseEntity<Flux<List<Todo>>> actual = this.apiContract.getNotifications();
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(todos, actual.getBody());
    }

    @Test
    void getNotificationsByDueDay() {
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setName("MyTodo 1");
        todo1.setActiveFlag(true);
        todo1.setAssignee("ABC1234");
        todo1.setDue("Today");
        todo1.setNotes("Some notes");

        Flux<List<Todo>> todos = Flux.just(List.of(todo1));
        when(this.service.getAllNotificationsByDueDay("Today")).thenReturn(todos);

        ResponseEntity<Flux<List<Todo>>> actual = this.apiContract.getNotificationsByDueDay("Today");
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(todos, actual.getBody());
    }

    @Test
    void getNotificationsByAssignee() {
        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setName("MyTodo 2");
        todo2.setActiveFlag(true);
        todo2.setAssignee("XYZ9876");
        todo2.setDue("Tomorrow");
        todo2.setNotes("Some notes for Todo 2");


        Flux<List<Todo>> todos = Flux.just(List.of(todo2));
        when(this.service.getNotificationsByAssignee("XYZ9876")).thenReturn(todos);

        ResponseEntity<Flux<List<Todo>>> actual = this.apiContract.getNotificationsByAssignee("XYZ9876");
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(todos, actual.getBody());
    }
}