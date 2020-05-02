package Server.RealTime;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/connect")
    //@SendTo("/topic/public")
    public Message connect(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("name", message.getContent());
        System.out.println("Recived Msg From Socket");
        return message;
    }


    @MessageMapping("/send")
    @SendTo("/topic/public")
    public Message send(@Payload Message message){
        return message;
    }
}
