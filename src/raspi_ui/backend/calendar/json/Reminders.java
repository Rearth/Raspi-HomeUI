
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
    "useDefault"
})
public class Reminders {

    @JsonProperty("useDefault")
    private Boolean useDefault;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Reminders() {
    }

    /**
     * 
     * @param useDefault
     */
    public Reminders(Boolean useDefault) {
        super();
        this.useDefault = useDefault;
    }

    @JsonProperty("useDefault")
    public Boolean getUseDefault() {
        return useDefault;
    }

    @JsonProperty("useDefault")
    public void setUseDefault(Boolean useDefault) {
        this.useDefault = useDefault;
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
        return new ToStringBuilder(this).append("useDefault", useDefault).append("additionalProperties", additionalProperties).toString();
    }

}
