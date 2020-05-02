package Publisher;

import DataAPI.OpCode;
import DataAPI.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public void notify(String userId){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        ResponseEntity<Response<String>> e=new ResponseEntity<>(new Response<>("notifyyyyyy", OpCode.Success), headers, HttpStatus.CREATED);
        messagingTemplate.convertAndSendToUser( userId,"/queue/greetings", e);
    }
}
