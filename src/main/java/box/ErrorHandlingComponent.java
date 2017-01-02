package box;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;


@Component
public class ErrorHandlingComponent{

    private final Logger log = LoggerFactory.getLogger(ErrorHandlingComponent.class);
    
    @EventListener
    @SendTo("/topic/error")
    public String raportMessage(String message){

        return message;
    }

}
