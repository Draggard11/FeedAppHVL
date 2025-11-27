package no.hvl.dat250.feedapphvl.apache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component

@Profile("!test")
public class KafkaListeners {

    @Autowired
    private PollWebSocketHandler ws;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "polls-events", groupId = "polls-group")
    public void handlePollEvent(String message) throws Exception {
        if (message.startsWith("new Vote on Poll:")) {
            Long pollId = Long.valueOf(message.split(":")[1]);

            ws.broadcastToPoll(pollId, message);
        }
    }

    @KafkaListener(topics = "users-events", groupId = "users-group")
    public void handleUserEvent(String message) {
        System.out.println("📩 Received user event: " + message);
    }
}