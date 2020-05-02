package Server.RealTime;

import java.security.Principal;
import java.util.Map;

import DataAPI.OpCode;
import DataAPI.Response;
import Publisher.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class WebSocketController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

   Publisher pub = new Publisher();

    @MessageMapping("/hello")
    @SendToUser("queue/greetings")
    public ResponseEntity<Response<String>> processMessageFromClient(@RequestBody String message, Principal principal) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        ResponseEntity<Response<String>> e=new ResponseEntity<>(new Response<>(message, OpCode.Success), headers, HttpStatus.CREATED);
//        messagingTemplate.convertAndSendToUser( principal.getName(),"/queue/greetings", e);
        pub.notify("0");
        System.out.println(principal.getName());
        Thread.sleep(5000);
        System.out.println("nivvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");

        return e;
    }

//    @MessageMapping("/hello")
//    @SendToUser("queue/greetings")
//    public String processMessageFromClient(@RequestBody String message, Principal principal) throws Exception {
//        messagingTemplate.convertAndSendToUser( principal.getName(),"/queue/greetings", "yuvallll");
//        System.out.println(principal.getName());
//        Thread.sleep(50000);
//        System.out.println("nivvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
//
//        return "yuvallll";
//    }

    @GetMapping("/test")
    public void test(){
        messagingTemplate.convertAndSend( "/topic/greetings", "tallll");
        //return "index";
    }


    @MessageExceptionHandler
    @SendToUser("/errors")
    public String handleException(Throwable exception) {
        System.out.println("error");
        return exception.getMessage();
    }

}
