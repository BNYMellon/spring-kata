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

package bny.training.spring.boot.todo;

public final class TodoConstants {
    //Exception messages
    public static final String INVALID_ID_MESSAGE = "Invalid id input [%s]";
    public static final String INVALID_NULL_ASSIGNEE_MESSAGE = "Invalid assignee input [%s]";
    public static final String INVALID_ASSIGNEE_LENGTH_MESSAGE = "Assignee [%s] length should be between 6 and 7 characters";
    public static final String TODO_NOT_FOUND_EXCEPTION = "A Todo with id [%s] was not found.";
}