package free.notification.scheduled;

import free.notification.api.ApiEvent;
import free.notification.config.BasicConfig;
import free.notification.config.ConfigState;
import free.notification.config.EmailConfig;
import free.notification.email.EMailSender;
import free.notification.mapper.EventMapper;
import free.notification.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class ScheduledTasks {

    @Value("${email.receiver}")
    private String receiver;

    @Autowired
    private BasicConfig basicConfig;

    @Autowired
    private EmailConfig emailConfig;

    private EMailSender eMailSender = null;

    @Autowired
    private EventService notificationService;

    @Scheduled(cron = "0 1 12 * * ?")
    public void sendStatus() {
        if (!ConfigState.SEND_SUMMARY) {
            return;
        }

        if (eMailSender == null) {
            eMailSender = new EMailSender(emailConfig);
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Source: " + basicConfig.getUrl());

        int counter = 0;
        Map<String, List<ApiEvent>> eventMap = listGrouped();
        for (Map.Entry<String, List<ApiEvent>> entry : eventMap.entrySet()) {
            if (counter == 0) {
                sb.append("\n");
            }

            sb.append("\n");
            sb.append("--- " + entry.getKey() + " ---");
            sb.append("\n");
            sb.append("\n");

            int i=0;
            for (ApiEvent event : entry.getValue()) {
                sb.append(event.getStatus());
                sb.append(" | ");
                sb.append(event.getCreated());
                sb.append(" | ");
                sb.append(event.getTitle());

                if (event.getDescription() != null) {
                    sb.append(" | ");
                    sb.append(event.getDescription());
                }

                if (event.getDetails() != null) {
                    sb.append(" | ");
                    sb.append(event.getDetails());
                }

                sb.append("\n");

                i++;
            }

            counter += entry.getValue().size();
        }

        if (eMailSender.hasValidConfiguration()) {
            eMailSender.sendEmail(receiver, "Events: " + counter, sb.toString());
        }
    }

    /**
     * Run on startup: Check email configuration
     */
    @Scheduled(initialDelay = 0, timeUnit = TimeUnit.DAYS, fixedDelay = 365 * 50) // 50 years
    public void verifyConfiguration() {
        if (emailConfig.getHost() == null || emailConfig.getHost().trim().isEmpty()) {
            System.err.println("Cannot send emails, host missing. Configure email server in application.properties (or environment variables: email.host, email.receiver, email.sender, email.username, email.password)");
        }
    }

    private Map<String, List<ApiEvent>> listGrouped() {
        return notificationService.listAll().stream().map(event -> EventMapper.map(event)).collect(Collectors.groupingBy(ApiEvent::getLocation));
    }
}
