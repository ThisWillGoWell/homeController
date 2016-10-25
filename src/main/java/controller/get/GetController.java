package controller.get;

/**
 * Created by Willi on 10/24/2016.
 */

import controller.Engine;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Controller
@EnableWebSocket
public class GetController  implements WebSocketConfigurer{

    Engine e;
    GetController(Engine engine)
    {

    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new GetGreeting(), "/get")
    }
}

