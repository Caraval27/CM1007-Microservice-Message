package journal.lab3_message.Core;

import journal.lab3_message.Core.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class Controller {
    @Autowired
    private MessageService messageService;

    @Autowired
    private HealthService healthService;

    public Controller() {}

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getLatestMessagesInThreads(@RequestParam String id) {
        try {
            /*OidcUser user = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            OidcIdToken token = user.getIdToken();
            if (!Objects.equals(token.getPreferredUsername(), id)) {
                return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
            }*/

            return ResponseEntity.ok(messageService.getLatestMessagesByUserInThread(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/messages_in_thread")
    public ResponseEntity<List<Message>> getMessagesInThread(@RequestParam int threadId) {
        try {
            return ResponseEntity.ok(messageService.getMessagesInThread(threadId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create_message")
    public ResponseEntity<Void> createNewMessage(@RequestBody CreateMessage newMessage) {
        try {
            String senderName = healthService.sendNameRequest(newMessage.getSenderId());

            String receiverId = newMessage.getReceiverId();
            if (receiverId == null) {
                receiverId = healthService.sendGeneralPractitionerRequest(newMessage.getSenderId());
            }

            String receiverName = healthService.sendNameRequest(receiverId);

            if (receiverName == null)
                ResponseEntity.badRequest().build();

            messageService.createNewMessage(newMessage, senderName, receiverId, receiverName);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/read_message")
    public ResponseEntity<Void> readMessage(@RequestParam String threadId, @RequestParam String receiverId) {
        try {
            messageService.updateMessageIsRead(threadId, receiverId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}