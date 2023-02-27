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

package bnymellon.training.spring.boot.todo.model.response;

import bnymellon.training.spring.boot.todo.model.Todo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "metadata",
        "todo"
})
public class TodoSuccessResponse implements TodoResponse {

    private static final long serialVersionUID = -6220322296496963477L;

    @JsonProperty("metadata")
    @ApiModelProperty(notes = "Container for any metadata for this request/response")
    private Metadata metadata = new Metadata();

    @JsonProperty("todo")
    @ApiModelProperty(notes = "A single todo returned as a payload")
    private Todo todo;

    public TodoSuccessResponse() {
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(final Metadata metadata) {
        this.metadata = metadata;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(final Todo todo) {
        this.todo = todo;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodoSuccessResponse that = (TodoSuccessResponse) o;

        if (metadata != null ? !metadata.equals(that.metadata) : that.metadata != null) return false;
        return todo != null ? todo.equals(that.todo) : that.todo == null;
    }

    @Override
    public int hashCode() {
        int result = metadata != null ? metadata.hashCode() : 0;
        result = 31 * result + (todo != null ? todo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TodoServiceResponse{");
        sb.append("metadata=").append(metadata);
        sb.append(", todo=").append(todo);
        sb.append('}');
        return sb.toString();
    }
}
