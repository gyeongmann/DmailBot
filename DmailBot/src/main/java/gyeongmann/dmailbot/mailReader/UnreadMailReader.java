package gyeongmann.dmailbot.mailReader;

import gyeongmann.dmailbot.bot.Bot;
import jakarta.mail.*;
import jakarta.mail.internet.MimeUtility;
import jakarta.mail.search.FlagTerm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class UnreadMailReader {

    @Value("${IMAP_HOST}")
    private String host;

    @Value("${IMAP_PORT}")
    private String port;

    @Value("${USER_NAME}")
    private String username;

    @Value("${PASSWORD}")
    private String password;

    public List<Message> getUnreadMessages(Bot bot) {
        List<Message> unread = new ArrayList<>();

        Properties props = new Properties();
        props.put("mail.store.protocol", "imap");
        props.put("mail.imap.host", host);
        props.put("mail.imap.port", port);
        props.put("mail.imap.ssl.enable", "true");

        try {
            Session session = Session.getDefaultInstance(props);
            Store store = session.getStore("imap");
            store.connect(username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            unread.addAll(Arrays.asList(messages));

            for (Message message : unread) {
                bot.sendText(7089252509L, print(message));
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
//            e.printStackTrace();
        }

        return unread;
    }

    private String print(Message message) throws MessagingException, UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append("📧 최근 메일 정보:").append('\n')
                .append("보낸 사람: ").append(MimeUtility.decodeText(message.getFrom()[0].toString())).append('\n')
                .append("제목: ").append(message.getSubject()).append('\n')
                .append("날짜: ").append(message.getSentDate()).append('\n');

        return sb.toString();
    }
}
