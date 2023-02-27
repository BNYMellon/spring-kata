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

package bnymellon.training.spring.boot.todo.service;

import bnymellon.training.spring.boot.todo.model.Todo;

import java.util.List;

public interface TodoService {

    Todo saveTodo(Todo todo);

    Todo getTodo(String todoId);

    List<Todo> getAllTodos(Boolean includeInactiveFlag);

    List<Todo> getTodosByAssignee(String assignee);

    Todo updateTodo(String todoId, Todo todo);

    Todo deleteTodo(String todoId);
}
