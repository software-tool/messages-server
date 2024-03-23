package free.notification.web;

import free.notification.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PostController {

    @Autowired
    private EventService notificationService;

    @PostMapping("/info")
    public void postNotification(@RequestParam String location,
                                 @RequestParam String title,
                                 @RequestParam String text) throws IOException {
        notificationService.add(location, title, text);
    }

}
