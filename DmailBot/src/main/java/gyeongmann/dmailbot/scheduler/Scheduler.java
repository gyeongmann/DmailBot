package gyeongmann.dmailbot.scheduler;

import gyeongmann.dmailbot.bot.Bot;
import gyeongmann.dmailbot.mailReader.UnreadMailReader;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final Bot bot;
    private final UnreadMailReader unreadMailReader;

    @Scheduled(cron = "0 0 9-18 ? * MON-FRI")
    public void run() {
        unreadMailReader.getUnreadMessages(bot);
    }
}
