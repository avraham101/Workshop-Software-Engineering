package Server.RealTime;

import com.google.gson.Gson;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

public class SocketHandler extends TextWebSocketHandler {


   // List sessions = new CopyOnWriteArrayList<>();


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        System.out.println("heyy");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //the messages will be broadcasted to all users.
        System.out.println("connect");
    }
}
