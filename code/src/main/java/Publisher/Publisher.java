package Publisher;

import DataAPI.Notification;
import DataAPI.OpCode;
import DataAPI.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.ArrayList;
import java.util.List;


public class Publisher {

    private final SimpMessageSendingOperations messagingTemplate;

    public Publisher(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate=messagingTemplate;
    }

    public void update(String userId, ArrayList<Notification> notification){
       messagingTemplate.convertAndSendToUser( userId,"/queue/greetings", notification);
    }
}
