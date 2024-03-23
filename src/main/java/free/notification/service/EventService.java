package free.notification.service;

import free.notification.config.BasicConfig;
import free.notification.config.ConfigState;
import free.notification.config.EmailConfig;
import free.notification.email.EMailSender;
import free.notification.model.Event;
import free.notification.persistence.EventPersistence;
import free.notification.state.EventListState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EventService {

    @Autowired
    private BasicConfig basicConfig;

    @Autowired
    private EmailConfig emailConfig;

    private EMailSender eMailSender = null;

    @Value("${email.receiver}")
    private String receiver;

    public List<Event> listAll() {
        List<Event> list = EventListState.getListFlat();

        log.debug("List all: {}", list);

        return list;
    }

    public List<Event> list(String location) {
        if (location != null && location.isEmpty()) {
            location = null;
        }

        List<Event> list = EventListState.getList(location);
        if (list == null) {
            list = new ArrayList<>();
        }

        log.debug("List: {}", location, list);

        //return list.stream().collect(Collectors.joining(","));
        return list;
    }

    public void clearAll(String location) throws IOException {
        log.info("Clear: {}", location);

        EventListState.clearAll();

        EventPersistence.write();
    }

    public void add(String location, String title, String description) throws IOException {
        log.info("Add: {} {}", location, title);

        String mailText = description;

        mailText += "\n\n";
        mailText += basicConfig.getUrl();

        Event event = new Event(location, title, description);
        EventListState.add(location, event);

        if (ConfigState.SEND_EMAILS) {
            if (eMailSender == null) {
                eMailSender = new EMailSender(emailConfig);
            }

            String subject = title;
            if (location != null && !location.trim().isEmpty()) {
                subject = "(" + location + ") " + title;
            }

            eMailSender.sendEmail(receiver, subject, mailText);
        }

        EventPersistence.write();
    }
}
