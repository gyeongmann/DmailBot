package gyeongmann.dmailbot.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String SENT_EMAIL_SET = "sent-email-ids";

    public boolean isAlreadySent(String messageId) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(SENT_EMAIL_SET, messageId));
    }

    public void markAsSent(String messageId) {
        redisTemplate.opsForSet().add(SENT_EMAIL_SET, messageId);
    }
}
