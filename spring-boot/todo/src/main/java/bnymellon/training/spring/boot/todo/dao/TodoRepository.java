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

package bnymellon.training.spring.boot.todo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bnymellon.training.spring.boot.todo.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByAssignee(String assignee);

    List<Todo> findByActiveFlagTrue();

    List<Todo> findByActiveFlagTrueAndAssignee(String assignee);

    // See: http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    Optional<List<Todo>> findByNotesContaining(String value);

    @Query(value = "SELECT t FROM Todo t WHERE t.notes LIKE %:value% ")
    Optional<List<Todo>> findCustomNotes(@Param("value") String value);

}
