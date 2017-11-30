
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
    "attendees",
    "created",
    "creator",
    "description",
    "end",
    "etag",
    "guestsCanModify",
    "hangoutLink",
    "htmlLink",
    "iCalUID",
    "id",
    "kind",
    "location",
    "organizer",
    "recurrence",
    "reminders",
    "sequence",
    "start",
    "status",
    "summary",
    "updated",
    "originalStartTime",
    "recurringEventId"
})
public class Item {

    @JsonProperty("attendees")
    private List<Attendee> attendees = null;
    @JsonProperty("created")
    private String created;
    @JsonProperty("creator")
    private Creator creator;
    @JsonProperty("description")
    private String description;
    @JsonProperty("end")
    private End end;
    @JsonProperty("etag")
    private String etag;
    @JsonProperty("guestsCanModify")
    private Boolean guestsCanModify;
    @JsonProperty("hangoutLink")
    private String hangoutLink;
    @JsonProperty("htmlLink")
    private String htmlLink;
    @JsonProperty("iCalUID")
    private String iCalUID;
    @JsonProperty("id")
    private String id;
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("location")
    private String location;
    @JsonProperty("organizer")
    private Organizer organizer;
    @JsonProperty("recurrence")
    private List<String> recurrence = null;
    @JsonProperty("reminders")
    private Reminders reminders;
    @JsonProperty("sequence")
    private Integer sequence;
    @JsonProperty("start")
    private Start start;
    @JsonProperty("status")
    private String status;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("updated")
    private String updated;
    @JsonProperty("originalStartTime")
    private OriginalStartTime originalStartTime;
    @JsonProperty("recurringEventId")
    private String recurringEventId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Item() {
    }

    /**
     * 
     * @param summary
     * @param reminders
     * @param attendees
     * @param etag
     * @param status
     * @param location
     * @param organizer
     * @param originalStartTime
     * @param guestsCanModify
     * @param kind
     * @param creator
     * @param id
     * @param recurrence
     * @param recurringEventId
     * @param updated
     * @param start
     * @param created
     * @param sequence
     * @param description
     * @param hangoutLink
     * @param iCalUID
     * @param end
     * @param htmlLink
     */
    public Item(List<Attendee> attendees, String created, Creator creator, String description, End end, String etag, Boolean guestsCanModify, String hangoutLink, String htmlLink, String iCalUID, String id, String kind, String location, Organizer organizer, List<String> recurrence, Reminders reminders, Integer sequence, Start start, String status, String summary, String updated, OriginalStartTime originalStartTime, String recurringEventId) {
        super();
        this.attendees = attendees;
        this.created = created;
        this.creator = creator;
        this.description = description;
        this.end = end;
        this.etag = etag;
        this.guestsCanModify = guestsCanModify;
        this.hangoutLink = hangoutLink;
        this.htmlLink = htmlLink;
        this.iCalUID = iCalUID;
        this.id = id;
        this.kind = kind;
        this.location = location;
        this.organizer = organizer;
        this.recurrence = recurrence;
        this.reminders = reminders;
        this.sequence = sequence;
        this.start = start;
        this.status = status;
        this.summary = summary;
        this.updated = updated;
        this.originalStartTime = originalStartTime;
        this.recurringEventId = recurringEventId;
    }

    @JsonProperty("attendees")
    public List<Attendee> getAttendees() {
        return attendees;
    }

    @JsonProperty("attendees")
    public void setAttendees(List<Attendee> attendees) {
        this.attendees = attendees;
    }

    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
    }

    @JsonProperty("creator")
    public Creator getCreator() {
        return creator;
    }

    @JsonProperty("creator")
    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("end")
    public End getEnd() {
        return end;
    }

    @JsonProperty("end")
    public void setEnd(End end) {
        this.end = end;
    }

    @JsonProperty("etag")
    public String getEtag() {
        return etag;
    }

    @JsonProperty("etag")
    public void setEtag(String etag) {
        this.etag = etag;
    }

    @JsonProperty("guestsCanModify")
    public Boolean getGuestsCanModify() {
        return guestsCanModify;
    }

    @JsonProperty("guestsCanModify")
    public void setGuestsCanModify(Boolean guestsCanModify) {
        this.guestsCanModify = guestsCanModify;
    }

    @JsonProperty("hangoutLink")
    public String getHangoutLink() {
        return hangoutLink;
    }

    @JsonProperty("hangoutLink")
    public void setHangoutLink(String hangoutLink) {
        this.hangoutLink = hangoutLink;
    }

    @JsonProperty("htmlLink")
    public String getHtmlLink() {
        return htmlLink;
    }

    @JsonProperty("htmlLink")
    public void setHtmlLink(String htmlLink) {
        this.htmlLink = htmlLink;
    }

    @JsonProperty("iCalUID")
    public String getICalUID() {
        return iCalUID;
    }

    @JsonProperty("iCalUID")
    public void setICalUID(String iCalUID) {
        this.iCalUID = iCalUID;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("organizer")
    public Organizer getOrganizer() {
        return organizer;
    }

    @JsonProperty("organizer")
    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    @JsonProperty("recurrence")
    public List<String> getRecurrence() {
        return recurrence;
    }

    @JsonProperty("recurrence")
    public void setRecurrence(List<String> recurrence) {
        this.recurrence = recurrence;
    }

    @JsonProperty("reminders")
    public Reminders getReminders() {
        return reminders;
    }

    @JsonProperty("reminders")
    public void setReminders(Reminders reminders) {
        this.reminders = reminders;
    }

    @JsonProperty("sequence")
    public Integer getSequence() {
        return sequence;
    }

    @JsonProperty("sequence")
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @JsonProperty("start")
    public Start getStart() {
        return start;
    }

    @JsonProperty("start")
    public void setStart(Start start) {
        this.start = start;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @JsonProperty("updated")
    public String getUpdated() {
        return updated;
    }

    @JsonProperty("updated")
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @JsonProperty("originalStartTime")
    public OriginalStartTime getOriginalStartTime() {
        return originalStartTime;
    }

    @JsonProperty("originalStartTime")
    public void setOriginalStartTime(OriginalStartTime originalStartTime) {
        this.originalStartTime = originalStartTime;
    }

    @JsonProperty("recurringEventId")
    public String getRecurringEventId() {
        return recurringEventId;
    }

    @JsonProperty("recurringEventId")
    public void setRecurringEventId(String recurringEventId) {
        this.recurringEventId = recurringEventId;
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
        return new ToStringBuilder(this).append("attendees", attendees).append("created", created).append("creator", creator).append("description", description).append("end", end).append("etag", etag).append("guestsCanModify", guestsCanModify).append("hangoutLink", hangoutLink).append("htmlLink", htmlLink).append("iCalUID", iCalUID).append("id", id).append("kind", kind).append("location", location).append("organizer", organizer).append("recurrence", recurrence).append("reminders", reminders).append("sequence", sequence).append("start", start).append("status", status).append("summary", summary).append("updated", updated).append("originalStartTime", originalStartTime).append("recurringEventId", recurringEventId).append("additionalProperties", additionalProperties).toString();
    }

}
