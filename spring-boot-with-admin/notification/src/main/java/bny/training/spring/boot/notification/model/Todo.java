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

package bny.training.spring.boot.notification.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "assignee",
        "due",
        "notes",
        "activeFlag"
})
public class Todo implements Serializable {

    private static final long serialVersionUID = 1771939609434516204L;

    @JsonPropertyDescription("The unique identifier, generated by TodoService on addition")
    private Long id;

    @JsonProperty(required = true)
    @JsonPropertyDescription("The unique name of the Todo")
    private String name;

    @JsonProperty(required = true)
    @JsonPropertyDescription("the assignee of the todo")
    private String assignee;

    @JsonPropertyDescription("A due date of the Todo")
    private String due;

    @JsonPropertyDescription("Any notes associated with the Todo")
    private String notes;

    @JsonProperty(defaultValue = "true")
    @JsonPropertyDescription("Active flag determining the status of the Todo")
    // NOTE: The activeFlag maps to a column of a different name: ACTIVE. This is done o purpose.
    private Boolean activeFlag = true;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAssignee()
    {
        return assignee;
    }

    public void setAssignee(String assignee)
    {
        this.assignee = assignee;
    }

    public String getDue()
    {
        return due;
    }

    public void setDue(String due)
    {
        this.due = due;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public Boolean getActiveFlag()
    {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag)
    {
        this.activeFlag = activeFlag;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        if (id != null ? !id.equals(todo.id) : todo.id != null) return false;
        if (name != null ? !name.equals(todo.name) : todo.name != null) return false;
        if (assignee != null ? !assignee.equals(todo.assignee) : todo.assignee != null) return false;
        if (due != null ? !due.equals(todo.due) : todo.due != null) return false;
        if (notes != null ? !notes.equals(todo.notes) : todo.notes != null) return false;
        return activeFlag != null ? activeFlag.equals(todo.activeFlag) : todo.activeFlag == null;
    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (assignee != null ? assignee.hashCode() : 0);
        result = 31 * result + (due != null ? due.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (activeFlag != null ? activeFlag.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("Todo{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", assignee='").append(assignee).append('\'');
        sb.append(", due='").append(due).append('\'');
        sb.append(", notes='").append(notes).append('\'');
        sb.append(", activeFlag=").append(activeFlag);
        sb.append('}');
        return sb.toString();
    }
}