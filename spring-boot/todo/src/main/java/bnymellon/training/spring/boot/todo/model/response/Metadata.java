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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "serviceName",
        "serviceVersion",
        "status",
        "description",
        "requestId",
        "requestTimestamp",
        "responseId",
        "responseTimestamp"
})
public class Metadata implements Serializable {

    private static final long serialVersionUID = 4537081829042146431L;

    @JsonProperty("serviceName")
    @ApiModelProperty(notes = "The service name that was called.")
    private String serviceName;

    @JsonProperty("serviceVersion")
    @ApiModelProperty(notes = "The service version that was called.")
    private String serviceVersion;

    @JsonProperty("status")
    @ApiModelProperty(notes = "The status of the operation.")
    private Status status = new Status();

    @JsonProperty("description")
    @ApiModelProperty(notes = "The status code associated with this status message.")
    private String description;

    @JsonProperty("requestId")
    @ApiModelProperty(notes = "The request id associated with this operation.")
    private String requestId;

    @JsonProperty("requestTimestamp")
    @ApiModelProperty(notes = "The timestamp that the request was received.")
    private String requestTimestamp;

    @JsonProperty("responseId")
    @ApiModelProperty(notes = "The response id associated with this operation.")
    private String responseId;

    @JsonProperty("responseTimestamp")
    @ApiModelProperty(notes = "The timestamp that the resppnse was generated.")
    private String responseTimestamp;

    public Metadata() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(final String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(final String requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public String getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(final String responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(final String requestId) {
        this.requestId = requestId;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(final String responseId) {
        this.responseId = responseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Metadata metadata = (Metadata) o;

        if (serviceName != null ? !serviceName.equals(metadata.serviceName) : metadata.serviceName != null)
            return false;
        if (serviceVersion != null ? !serviceVersion.equals(metadata.serviceVersion) : metadata.serviceVersion != null)
            return false;
        if (requestTimestamp != null ? !requestTimestamp.equals(metadata.requestTimestamp) : metadata.requestTimestamp != null)
            return false;
        if (responseTimestamp != null ? !responseTimestamp.equals(metadata.responseTimestamp) : metadata.responseTimestamp != null)
            return false;
        if (status != null ? !status.equals(metadata.status) : metadata.status != null) return false;
        if (requestId != null ? !requestId.equals(metadata.requestId) : metadata.requestId != null) return false;
        if (responseId != null ? !responseId.equals(metadata.responseId) : metadata.responseId != null) return false;
        return description != null ? description.equals(metadata.description) : metadata.description == null;
    }

    @Override
    public int hashCode() {
        int result = serviceName != null ? serviceName.hashCode() : 0;
        result = 31 * result + (serviceVersion != null ? serviceVersion.hashCode() : 0);
        result = 31 * result + (requestTimestamp != null ? requestTimestamp.hashCode() : 0);
        result = 31 * result + (responseTimestamp != null ? responseTimestamp.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (requestId != null ? requestId.hashCode() : 0);
        result = 31 * result + (responseId != null ? responseId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Metadata{");
        sb.append("serviceName='").append(serviceName).append('\'');
        sb.append(", serviceVersion='").append(serviceVersion).append('\'');
        sb.append(", requestTimestamp='").append(requestTimestamp).append('\'');
        sb.append(", responseTimestamp='").append(responseTimestamp).append('\'');
        sb.append(", status=").append(status);
        sb.append(", requestId='").append(requestId).append('\'');
        sb.append(", responseId='").append(responseId).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
