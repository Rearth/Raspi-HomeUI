
package raspi_ui.backend.calendar.json;

import java.util.HashMap;
import java.util.List;
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
    "accessRole",
    "defaultReminders",
    "description",
    "etag",
    "items",
    "kind",
    "nextSyncToken",
    "summary",
    "timeZone",
    "updated"
})
public class CalenderInfo {

    @JsonProperty("accessRole")
    private String accessRole;
    @JsonProperty("defaultReminders")
    private List<Object> defaultReminders = null;
    @JsonProperty("description")
    private String description;
    @JsonProperty("etag")
    private String etag;
    @JsonProperty("items")
    private List<Item> items = null;
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("nextSyncToken")
    private String nextSyncToken;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("timeZone")
    private String timeZone;
    @JsonProperty("updated")
    private String updated;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public CalenderInfo() {
    }

    /**
     * 
     * @param summary
     * @param defaultReminders
     * @param etag
     * @param updated
     * @param items
     * @param description
     * @param timeZone
     * @param nextSyncToken
     * @param accessRole
     * @param kind
     */
    public CalenderInfo(String accessRole, List<Object> defaultReminders, String description, String etag, List<Item> items, String kind, String nextSyncToken, String summary, String timeZone, String updated) {
        super();
        this.accessRole = accessRole;
        this.defaultReminders = defaultReminders;
        this.description = description;
        this.etag = etag;
        this.items = items;
        this.kind = kind;
        this.nextSyncToken = nextSyncToken;
        this.summary = summary;
        this.timeZone = timeZone;
        this.updated = updated;
    }

    @JsonProperty("accessRole")
    public String getAccessRole() {
        return accessRole;
    }

    @JsonProperty("accessRole")
    public void setAccessRole(String accessRole) {
        this.accessRole = accessRole;
    }

    @JsonProperty("defaultReminders")
    public List<Object> getDefaultReminders() {
        return defaultReminders;
    }

    @JsonProperty("defaultReminders")
    public void setDefaultReminders(List<Object> defaultReminders) {
        this.defaultReminders = defaultReminders;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("etag")
    public String getEtag() {
        return etag;
    }

    @JsonProperty("etag")
    public void setEtag(String etag) {
        this.etag = etag;
    }

    @JsonProperty("items")
    public List<Item> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<Item> items) {
        this.items = items;
    }

    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    @JsonProperty("nextSyncToken")
    public String getNextSyncToken() {
        return nextSyncToken;
    }

    @JsonProperty("nextSyncToken")
    public void setNextSyncToken(String nextSyncToken) {
        this.nextSyncToken = nextSyncToken;
    }

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @JsonProperty("timeZone")
    public String getTimeZone() {
        return timeZone;
    }

    @JsonProperty("timeZone")
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @JsonProperty("updated")
    public String getUpdated() {
        return updated;
    }

    @JsonProperty("updated")
    public void setUpdated(String updated) {
        this.updated = updated;
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
        return new ToStringBuilder(this).append("accessRole", accessRole).append("defaultReminders", defaultReminders).append("description", description).append("etag", etag).append("items", items).append("kind", kind).append("nextSyncToken", nextSyncToken).append("summary", summary).append("timeZone", timeZone).append("updated", updated).append("additionalProperties", additionalProperties).toString();
    }

}
