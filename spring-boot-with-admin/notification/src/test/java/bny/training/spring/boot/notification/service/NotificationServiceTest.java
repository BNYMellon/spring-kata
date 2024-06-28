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

package bny.training.spring.boot.notification.service;

import bny.training.spring.boot.notification.model.response.TodoCollectionResponse;
import bny.training.spring.boot.notification.model.Todo;
import org.apache.http.client.ResponseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class NotificationServiceTest {

    @MockBean
    private WebClient.Builder webClientBuilder;

    @MockBean
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @MockBean
    private WebClient.RequestBodySpec requestBodySpec;

    @MockBean
    private ResponseHandler responseHandler;

    private TodoCollectionResponse response;
    private Todo todo1;
    private Todo todo2;

    private NotificationServiceImpl service;

    @BeforeEach
    void setUp() {
        this.webClientBuilder = Mockito.mock(WebClient.Builder.class);
        this.webClient = Mockito.mock(WebClient.class);

        todo1 = new Todo();
        todo1.setId(1L);
        todo1.setName("MyTodo 1");
        todo1.setActiveFlag(true);
        todo1.setAssignee("ABC1234");
        todo1.setDue("Today");
        todo1.setNotes("Some notes");

        todo2 = new Todo();
        todo2.setId(2L);
        todo2.setName("MyTodo 2");
        todo2.setActiveFlag(true);
        todo2.setAssignee("XYZ9876");
        todo2.setDue("Tomorrow");
        todo2.setNotes("Some notes for Todo 2");

        response = new TodoCollectionResponse();
        List<Todo> todos = List.of(todo1, todo2);
        response.setTodos(todos);

        when(this.webClientBuilder.uriBuilderFactory(any())).thenReturn(webClientBuilder);
        when(this.webClientBuilder.build()).thenReturn(webClient);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        service = new NotificationServiceImpl(webClientBuilder);
    }

    @Test
    void getAllNotifications() {

        when(requestHeadersUriSpec.uri("/todos")).thenReturn(requestBodySpec);
        when(requestBodySpec.exchangeToMono(any())).thenReturn(Mono.just(response));
        Flux<List<Todo>> actual = service.getAllNotifications();

        StepVerifier.create(actual)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(1L))
                .expectNext(List.of(todo1, todo2))
                .thenCancel()
                .verify();
    }

    @Test
    void getAllNotificationsError() {

        when(requestHeadersUriSpec.uri("/todos")).thenReturn(requestBodySpec);
        when(requestBodySpec.exchangeToMono(any())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        Flux<List<Todo>> actual = service.getAllNotifications();

        StepVerifier.create(actual)
                .expectSubscription()
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                        throwable.getMessage().equals("400 BAD_REQUEST"))
                .verify();
    }

    @Test
    void getAllNotificationsByDueDay() {

        when(requestHeadersUriSpec.uri("/todos")).thenReturn(requestBodySpec);
        when(requestBodySpec.exchangeToMono(any())).thenReturn(Mono.just(response));
        Flux<List<Todo>> actual = service.getAllNotificationsByDueDay("Today");

        StepVerifier.create(actual)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(1L))
                .expectNext(List.of(todo1))
                .thenCancel()
                .verify();
    }

    @Test
    void getNotificationsByDueDayError() {

        when(requestHeadersUriSpec.uri("/todos")).thenReturn(requestBodySpec);
        when(requestBodySpec.exchangeToMono(any())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        Flux<List<Todo>> actual = service.getAllNotificationsByDueDay("Today");

        StepVerifier.create(actual)
                .expectSubscription()
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                        throwable.getMessage().equals("400 BAD_REQUEST"))
                .verify();
    }

    @Test
    void getNotificationsByAssignee() {

        response.setTodos(List.of(todo2));

        when(requestHeadersUriSpec.uri("/todos/assignees/{assignee}", "XYZ9876")).thenReturn(requestBodySpec);
        when(requestBodySpec.exchangeToMono(any())).thenReturn(Mono.just(response));
        Flux<List<Todo>> actual = service.getNotificationsByAssignee("XYZ9876");

        StepVerifier.create(actual)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(1L))
                .expectNext(List.of(todo2))
                .thenCancel()
                .verify();
    }

    @Test
    void getNotificationsByAssigneeError() {
        when(requestHeadersUriSpec.uri("/todos/assignees/{assignee}", "XYZ9876")).thenReturn(requestBodySpec);
        when(requestBodySpec.exchangeToMono(any())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        Flux<List<Todo>> actual = service.getNotificationsByAssignee("XYZ9876");

        StepVerifier.create(actual)
                .expectSubscription()
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                        throwable.getMessage().equals("400 BAD_REQUEST"))
                .verify();
    }
}