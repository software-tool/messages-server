package free.notification.api;

import free.notification.model.EventSeverity;
import free.notification.model.EventStatus;
import free.notification.model.EventType;
import lombok.Value;

import java.util.Date;

@Value
public class ApiEvent {

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

    private String details;

    public ApiEvent(Date created, String location, String title, String description, String details) {
        this.created = created;

        this.location = location;

        this.title = title;
        this.description = description;
        this.details = details;
    }
}
