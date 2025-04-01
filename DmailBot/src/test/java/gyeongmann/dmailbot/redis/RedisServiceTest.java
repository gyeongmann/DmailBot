package gyeongmann.dmailbot.redis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RedisServiceTest {

    private RedisTemplate<String, String> redisTemplate;
    private SetOperations<String, String> setOperations;
    private RedisService redisService;

    @BeforeEach
    void setUp() {
        redisTemplate = Mockito.mock(RedisTemplate.class);
        setOperations = Mockito.mock(SetOperations.class);
        when(redisTemplate.opsForSet()).thenReturn(setOperations);

        redisService = new RedisService(redisTemplate);
    }

    @Test
    void testIsAlreadySent() {
        // given
        String messageId = "msg-001";
        when(setOperations.isMember("sent-email-ids", messageId)).thenReturn(false);

        // when
        boolean result = redisService.isAlreadySent(messageId);

        // then
        assertThat(result).isFalse();
        verify(setOperations).isMember("sent-email-ids", messageId);
    }

    @Test
    void testMarkAsSent() {
        String messageId = "msg-002";

        redisService.markAsSent(messageId);

        verify(setOperations).add("sent-email-ids", messageId);
    }
}
