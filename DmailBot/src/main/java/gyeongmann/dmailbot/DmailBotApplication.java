package gyeongmann.dmailbot;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DmailBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(DmailBotApplication.class, args);
    }

}
