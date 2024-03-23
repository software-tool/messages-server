package free.notification.model;

import lombok.Value;

import java.util.Date;

@Value
public class Event {

    private Date created;
    private Date updated = null;

    private String location;

    private String title;
    private String description;

    private EventType type = EventType.MESSAGE;
    private EventStatus status = EventStatus.NONE;
    private EventSeverity severity = EventSeverity.NONE;

    private String reference1 = null;
    private String reference2 = null;
    private String reference3 = null;

    private String details = null;

    public Event(Date created, String location, String title, String description) {
        this.created = created;

        this.location = location;

        this.title = title;
        this.description = description;
    }

    public Event(String location, String title, String description) {
        this.created = new Date();
        this.location = location;

        this.title = title;
        this.description = description;
    }

}
