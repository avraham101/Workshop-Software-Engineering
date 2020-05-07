package Server.RealTime;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebSocketController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/hello")
    @SendToUser("/queue/greetings")
    public String processMessageFromClient(@Payload String message, Principal principal) throws Exception {
        // messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/reply", "name");
        System.out.println("nivvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        Thread.sleep(1000);
        return "name";
    }

    @GetMapping("/test")
    public String test()
    {
        System.out.println("dasd");
        return "index";
    }

    @MessageExceptionHandler
    @SendToUser("/error")
    public String handleException(Throwable exception) {
        System.out.println(exception.getMessage());
        return exception.getMessage();
    }

}
