package no.hvl.dat250.feedapphvl.apache;

import no.hvl.dat250.feedapphvl.domain.Poll;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Profile("!test")
public class PollWebSocketHandler extends TextWebSocketHandler {

    private final Map<Long, List<WebSocketSession>> pollSubscribers = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();

        if (msg.startsWith("SUBSCRIBE:")) {
            Long pollId = Long.valueOf(msg.split(":")[1]);

            pollSubscribers
                    .computeIfAbsent(pollId, id -> new ArrayList<>())
                    .add(session);
        }
    }

    public void broadcastToPoll(Long pollId, String message) throws Exception {
        var sessions = pollSubscribers.getOrDefault(pollId, List.of());
        for (WebSocketSession s : sessions) {
            s.sendMessage(new TextMessage(message));
        }
    }

}
