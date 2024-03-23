package free.notification.web;

import free.notification.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// TODO: Make server configurable
@RestController
public class ConfigController {

    @Autowired
    private EventService notificationService;

    //@PostMapping("/configure")
    public void postConfiguration(@RequestParam String location, @RequestParam String text) {
    }

}
