package gyeongmann.dmailbot.scheduler;

import gyeongmann.dmailbot.bot.Bot;
import gyeongmann.dmailbot.mailReader.UnreadMailReader;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final Bot bot;
    private final UnreadMailReader unreadMailReader;

    @Scheduled(cron = "0 * 9-17 ? * MON-FRI")
    public void run() {
        LocalTime now = LocalTime.now();

        if (now.getMinute() == 0) {
            System.out.println("⏰ 정각 실행됨: " + now);
        }
        unreadMailReader.getUnreadMessages(bot);
    }
}
