package info.mastera.userapi;

import info.mastera.userapi.config.KeycloakSettings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableConfigurationProperties(KeycloakSettings.class)
@EnableFeignClients
@SpringBootApplication
public class UserApiApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }
    
}
