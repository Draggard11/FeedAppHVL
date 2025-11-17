package no.hvl.dat250.feedapphvl.apache;

import no.hvl.dat250.feedapphvl.domain.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "polls-events", groupId = "polls-group")
    public void handlePollEvent(String message) {
        System.out.println("📩 Received poll event: " + message);
        // broadcast to all connected clients
        //TODO update database
        messagingTemplate.convertAndSend("/topic/polls", message);
    }

    @KafkaListener(topics = "users-events", groupId = "users-group")
    public void handleUserEvent(String message) {
        System.out.println("📩 Received user event: " + message);
    }
}