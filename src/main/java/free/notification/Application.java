package free.notification;

import free.notification.model.Event;
import free.notification.persistence.EventPersistence;
import free.notification.state.EventListState;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.List;

@EnableScheduling
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        //System.out.println("Run in: " + new File(".").getAbsolutePath());

        // Read persistent data
        readEvents();

        SpringApplication.run(Application.class, args);
    }

    private static void readEvents() {
        try {
            List<Event> events = EventPersistence.read();
            EventListState.apply(events);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
