
package raspi_ui.backend.calendar.json;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "email",
    "responseStatus",
    "displayName",
    "resource"
})
public class Attendee {

    @JsonProperty("email")
    private String email;
    @JsonProperty("responseStatus")
    private String responseStatus;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("resource")
    private Boolean resource;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Attendee() {
    }

    /**
     * 
     * @param email
     * @param responseStatus
     * @param resource
     * @param displayName
     */
    public Attendee(String email, String responseStatus, String displayName, Boolean resource) {
        super();
        this.email = email;
        this.responseStatus = responseStatus;
        this.displayName = displayName;
        this.resource = resource;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("responseStatus")
    public String getResponseStatus() {
        return responseStatus;
    }

    @JsonProperty("responseStatus")
    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    @JsonProperty("displayName")
    public String getDisplayName() {
        return displayName;
    }

    @JsonProperty("displayName")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @JsonProperty("resource")
    public Boolean getResource() {
        return resource;
    }

    @JsonProperty("resource")
    public void setResource(Boolean resource) {
        this.resource = resource;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("email", email).append("responseStatus", responseStatus).append("displayName", displayName).append("resource", resource).append("additionalProperties", additionalProperties).toString();
    }

}
