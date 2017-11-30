
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
    "displayName",
    "email",
    "self"
})
public class Organizer {

    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("self")
    private Boolean self;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Organizer() {
    }

    /**
     * 
     * @param email
     * @param self
     * @param displayName
     */
    public Organizer(String displayName, String email, Boolean self) {
        super();
        this.displayName = displayName;
        this.email = email;
        this.self = self;
    }

    @JsonProperty("displayName")
    public String getDisplayName() {
        return displayName;
    }

    @JsonProperty("displayName")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("self")
    public Boolean getSelf() {
        return self;
    }

    @JsonProperty("self")
    public void setSelf(Boolean self) {
        this.self = self;
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
        return new ToStringBuilder(this).append("displayName", displayName).append("email", email).append("self", self).append("additionalProperties", additionalProperties).toString();
    }

}
