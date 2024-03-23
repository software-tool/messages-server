package free.notification.state;

import free.notification.model.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventListState {

    private static final String LOCATION_DEFAULT = "_def";

    public static Map<String, List<Event>> events = new HashMap<>();

    public static void apply(List<Event> newEvents) {
        for (Event event : newEvents) {
            String location = event.getLocation();

            List<Event> list = getList(location);
            if (list == null) {
                list = new ArrayList<>();
                events.put(location, list);
            }
            list.add(event);
        }
    }

    public static List<Event> getListFlat() {
        List<Event> list = new ArrayList<>();
        for (Map.Entry<String, List<Event>> entry : events.entrySet()) {
            list.addAll(entry.getValue());
        }

        return list;
    }

    public static List<Event> getList(String location) {
        return events.get(location);
    }

    public static void add(String location, Event event) {
        if (location == null) {
            location = LOCATION_DEFAULT;
        }

        List<Event> list = getList(location);
        if (list == null) {
            list = new ArrayList<>();
            events.put(location, list);
        }
        list.add(event);
    }

    public static void clearAll() {
        events.clear();
    }
}
