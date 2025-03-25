package gyeongmann.dmailbot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {

    private final String BOT_API_KEY;

    public Bot(@Value("${BOT_API_KEY}") String BOT_API_KEY) {
        this.BOT_API_KEY = BOT_API_KEY;
    }

    @Override
    public String getBotUsername() {
        return "Daum_Mail_bot";
    }

    @Override
    public String getBotToken() {
        return BOT_API_KEY;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();
        var chatId = msg.getChatId();

        System.out.println(user.getLastName() + user.getFirstName() + " wrote " + msg.getText() + "/ chatId: " + chatId);
    }

    public void sendText(Long who, String what) {
        int maxLength = 4096;
        int length = what.length();

        for (int i = 0; i < length; i += maxLength) {
            String chunk = what.substring(i, Math.min(length, i + maxLength));
            SendMessage sm = SendMessage.builder()
                    .chatId(who.toString())
                    .text(chunk)
                    .build();

            try {
                execute(sm);  // 메시지 전송
            } catch (TelegramApiException e) {
                throw new RuntimeException("Error sending message: " + e.getMessage(), e);
            }
        }
    }
}
