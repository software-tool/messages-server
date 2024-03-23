package free.notification.persistence;

import free.notification.model.Event;
import free.notification.state.EventListState;
import recordlib.Record;
import recordlib.RecordDef;
import recordlib.json.read.RecordReaderJson;
import recordlib.json.read.RecordReaderUsingSpec;
import recordlib.json.write.RecordWriterJson;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Read/Write messages to file
 *
 * Uses library "recordlib-java":
 * https://github.com/software-tool/recordlib-java
 */
public class EventPersistence {

    private static File fileEvent = new File("events.json");

    private static RecordDef recordDefEvent;
    static {
        recordDefEvent = new RecordDef("event")
                .addDate("created")
                .add("location")
                .add("title")
                .add("description");
    }

    public static List<Event> read() throws IOException {
        List<Record> records = RecordReaderUsingSpec.readItemsFromFile(fileEvent, recordDefEvent);
        return records.stream().map(record -> toEvent(record)).toList();
    }

    public static void write() throws IOException {
        List<Record> records = EventListState.getListFlat().stream().map(event -> toRecord(event)).toList();
        RecordWriterJson.writeToFile(fileEvent, records, recordDefEvent);
    }

    private static Record toRecord(Event event) {
        Record record = new Record();
        record.set("created", event.getCreated());
        record.set("location", event.getLocation());
        record.set("title", event.getTitle());
        record.set("description", event.getDescription());
        return record;
    }

    private static Event toEvent(Record record) {
        Event event = new Event(record.getDate("created"), record.get("location"), record.get("title"), record.get("description"));
        return event;
    }
}
