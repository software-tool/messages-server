package free.notification.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "email")
@Configuration
@Getter
@Setter
public class EmailConfig {

    private String host;
    private String sender;
    private String username;
    private String password;

}
