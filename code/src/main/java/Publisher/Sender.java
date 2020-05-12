package Publisher;

import DataAPI.Notification;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.ArrayList;

public class Sender {

    private final SimpMessageSendingOperations messagingTemplate;

    public Sender(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate=messagingTemplate;
    }

    public void send(String userId, ArrayList<Notification> notification){
        messagingTemplate.convertAndSendToUser( userId,"/queue/greetings", notification);
    }
}
