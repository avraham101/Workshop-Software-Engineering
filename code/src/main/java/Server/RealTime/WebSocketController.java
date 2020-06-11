package Server.RealTime;

import java.security.Principal;

import Publisher.*;
import Service.SingleService;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;


@Controller
public class WebSocketController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final Publisher pub;

    public WebSocketController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        pub = new Publisher(new SpringSender(messagingTemplate));
        SinglePublisher.initPublisher(pub);
    }

    @MessageMapping("/hello")
    @SendToUser("/queue/greetings")
    public String processMessageFromClient(@Payload String message, Principal principal) throws Exception {
        SingleService.getInstance().getMyNotification(Integer.parseInt(principal.getName()));
        return "";
    }

    @MessageExceptionHandler
    @SendToUser("/error")
    public String handleException(Throwable exception) {
        System.out.println(exception.getMessage());
        return exception.getMessage();
    }

}
