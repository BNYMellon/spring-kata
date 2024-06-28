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

package bny.training.spring.boot.todo.model.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "success",
        "statusCode",
        "statusDescription"
})
public class Status implements Serializable {

    private static final long serialVersionUID = -3384894274119064560L;

    @JsonProperty("statusCode")
    @ApiModelProperty(notes = "The status code associated with this status message.")
    private String statusCode;

    @JsonProperty("success")
    @ApiModelProperty(notes = "A boolean flag to indicate the success of the operation.", example = "true")
    private boolean success;

    @JsonProperty("statusDescription")
    @ApiModelProperty(notes = "The status message associated with this status code.")
    private String statusDescription;

    public Status() {
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(final String statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(final String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Status status = (Status) o;

        if (success != status.success) return false;
        if (statusCode != null ? !statusCode.equals(status.statusCode) : status.statusCode != null) return false;
        return statusDescription != null ? statusDescription.equals(status.statusDescription) : status.statusDescription == null;
    }

    @Override
    public int hashCode() {
        int result = statusCode != null ? statusCode.hashCode() : 0;
        result = 31 * result + (success ? 1 : 0);
        result = 31 * result + (statusDescription != null ? statusDescription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Status{");
        sb.append("statusCode='").append(statusCode).append('\'');
        sb.append(", success=").append(success);
        sb.append(", statusDescription='").append(statusDescription).append('\'');
        sb.append('}');
        return sb.toString();
    }
}