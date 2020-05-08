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
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        ResponseEntity<Response<List<Notification>>> e=new ResponseEntity<>(new Response<>(notification, OpCode.Success), headers, HttpStatus.CREATED);
        messagingTemplate.convertAndSendToUser( userId,"/queue/greetings", e);
    }
}
