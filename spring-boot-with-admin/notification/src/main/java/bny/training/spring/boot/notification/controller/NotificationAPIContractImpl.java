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

package bny.training.spring.boot.notification.controller;

import bny.training.spring.boot.notification.model.Todo;
import bny.training.spring.boot.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class NotificationAPIContractImpl implements NotificationAPIContract {

    private final NotificationService notificationService;

    @Autowired
    public NotificationAPIContractImpl(NotificationService notificationService)
    {
        this.notificationService = notificationService;
    }

    @Override
    public ResponseEntity<Flux<List<Todo>>> getNotifications()
    {
        return ResponseEntity.ok(this.notificationService.getAllNotifications());
    }

    @Override
    public ResponseEntity<Flux<List<Todo>>> getNotificationsByDueDay(String dueDay)
    {
        return ResponseEntity.ok(this.notificationService.getAllNotificationsByDueDay(dueDay));
    }

    @Override
    public ResponseEntity<Flux<List<Todo>>> getNotificationsByAssignee(String assignee)
    {
        return ResponseEntity.ok(this.notificationService.getNotificationsByAssignee(assignee));
    }
}
