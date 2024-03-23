package free.notification.web;

import free.notification.api.ApiEvent;
import free.notification.mapper.EventMapper;
import free.notification.model.Event;
import free.notification.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ListingController {

    @Autowired
    private EventService notificationService;

    @GetMapping("/listAll")
    public List<ApiEvent> listAllNotifications() {
        return notificationService.listAll().stream().map(event -> EventMapper.map(event)).toList();
    }

    @GetMapping("/listGrouped")
    public Map<String, List<ApiEvent>> listGrouped() {
        return notificationService.listAll().stream().map(event -> EventMapper.map(event)).collect(Collectors.groupingBy(ApiEvent::getLocation));
    }

    @GetMapping("/list")
    public List<ApiEvent> listNotifications(@RequestParam String location) {
        return notificationService.list(location).stream().map(event -> EventMapper.map(event)).toList();
    }

    @PostMapping("/clear")
    public void clearAll(@RequestParam String location) throws IOException {
        notificationService.clearAll(location);
    }
}
