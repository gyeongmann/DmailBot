package gyeongmann.dmailbot.scheduler;

import gyeongmann.dmailbot.bot.Bot;
import gyeongmann.dmailbot.mailReader.UnreadMailReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class Scheduler {

    private final Bot bot;
    private final UnreadMailReader unreadMailReader;

    @Scheduled(cron = "0 * 9-23 ? * MON-FRI")
    public void run() {
        LocalTime now = LocalTime.now();

        log.info("--- success running -----" + now);
        unreadMailReader.getUnreadMessages(bot);
    }
}
