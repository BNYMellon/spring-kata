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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.retry.Repeat;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final String TODO_ALL = "/todos";
    private static final String TODO_BY_ASSIGNEE = "/todos/assignees/{assignee}";
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final WebClient webClient;

    public NotificationServiceImpl(WebClient.Builder webClientBuilder)
    {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory("http://localhost:8060");
        this.webClient = webClientBuilder.uriBuilderFactory(factory).build();
    }

    @Override
    public Flux<List<Todo>> getAllNotifications()
    {
        return this.webClient.get().uri(TODO_ALL)
                .exchangeToMono(clientResponse -> {
                    if (HttpStatus.OK == clientResponse.statusCode())
                    {
                        return clientResponse.bodyToMono(TodoCollectionResponse.class);
                    }
                    return clientResponse.createException().flatMap(Mono::error);
                })
                .map(TodoCollectionResponse::getTodos)
                .publishOn(Schedulers.fromExecutor(this.executor))
                .delayElement(Duration.ofSeconds(1L))
                .repeatWhen(Repeat.onlyIf(repeatContext -> true))
                .distinctUntilChanged();
    }

    @Override
    public Flux<List<Todo>> getAllNotificationsByDueDay(String dueDay)
    {
        return this.webClient.get().uri(TODO_ALL)
                        .exchangeToMono(clientResponse -> {
                            if (HttpStatus.OK == clientResponse.statusCode())
                            {
                                return clientResponse.bodyToMono(TodoCollectionResponse.class);
                            }
                            return clientResponse.createException().flatMap(Mono::error);
                        })
                        .map(TodoCollectionResponse::getTodos)
                        .map(todos -> todos.stream().filter(todo -> todo.getDue().equals(dueDay)).collect(Collectors.toList()))
                        .publishOn(Schedulers.fromExecutor(this.executor))
                        .delayElement(Duration.ofSeconds(5L))
                        .repeatWhen(Repeat.onlyIf(repeatContext -> true))
                        .distinctUntilChanged();
    }

    @Override
    public Flux<List<Todo>> getNotificationsByAssignee(String assignee)
    {
        return this.webClient.get().uri(TODO_BY_ASSIGNEE, assignee)
                .exchangeToMono(clientResponse -> {
                    if (HttpStatus.OK == clientResponse.statusCode())
                    {
                        return clientResponse.bodyToMono(TodoCollectionResponse.class);
                    }
                    return clientResponse.createException().flatMap(Mono::error);
                })
        .map(TodoCollectionResponse::getTodos)
                .publishOn(Schedulers.fromExecutor(this.executor))
                .delayElement(Duration.ofSeconds(5L))
                .repeatWhen(Repeat.onlyIf(repeatContext -> true))
                .distinctUntilChanged();
    }
}
