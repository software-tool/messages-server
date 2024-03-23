package free.notification.mapper;

import free.notification.api.ApiEvent;
import free.notification.model.Event;

public class EventMapper {

    public static ApiEvent map(Event event) {
        ApiEvent apiEvent = new ApiEvent(event.getCreated(), event.getLocation(),
                event.getTitle(), event.getDescription(), event.getDetails());
        return apiEvent;
    }

}
