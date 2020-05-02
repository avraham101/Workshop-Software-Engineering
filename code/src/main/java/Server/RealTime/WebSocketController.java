package Server.RealTime;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

    @MessageMapping("/hello")
    @SendToUser("queue/greetings")
    public String processMessageFromClient(@RequestBody String message,Principal principal) throws Exception {
        messagingTemplate.convertAndSendToUser( principal.getName(),"/queue/greetings", "yuvall");
        System.out.println(principal.getName());
        Thread.sleep(5000);
        System.out.println("nivvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        return message;
    }

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
