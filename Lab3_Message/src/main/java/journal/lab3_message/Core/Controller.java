package journal.lab3_message.Core;

import journal.lab3_message.Core.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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
            Jwt token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userId = token.getClaimAsString("preferred_username").toUpperCase();
            if (!Objects.equals(userId, id)) {
                return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();
            }

            return ResponseEntity.ok(messageService.getLatestMessagesByUserInThread(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/messages_in_thread")
    public ResponseEntity<List<Message>> getMessagesInThread(@RequestParam int threadId) {
        try {
            List<Message> messages = messageService.getMessagesInThread(threadId);
            Jwt token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userId = token.getClaimAsString("preferred_username").toUpperCase();
            for (Message message : messages) {
                if (Objects.equals(message.getSenderId(), userId) || Objects.equals(message.getReceiverId(), userId)) {
                    return ResponseEntity.ok(messages);
                }
            }
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    private boolean isAuthorizedById(String senderId) {
        Jwt token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = token.getClaimAsString("preferred_username").toUpperCase();
        return userId.equals(senderId);
    }

    @PostMapping("/create_message")
    public ResponseEntity<Void> createNewMessage(@RequestBody CreateMessage message) {
        try {
            Jwt token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!isAuthorizedById(message.getSenderId())) {
                return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();
            }
            String senderName = healthService.sendNameRequest(message.getSenderId(), token);
            if (!isAuthorizedById(message.getSenderId())) {
                return ResponseEntity.badRequest().build();
            }
            String receiverId = message.getReceiverId();
            if (receiverId == null) {
                receiverId = healthService.sendGeneralPractitionerRequest(message.getSenderId(), token);
                if (!isAuthorizedById(message.getSenderId())) {
                    return ResponseEntity.badRequest().build();
                }
            }

            String receiverName = healthService.sendNameRequest(receiverId, token);
            if (!isAuthorizedById(message.getSenderId())) {
                return ResponseEntity.badRequest().build();
            }

            if (receiverName == null)
                ResponseEntity.badRequest().build();

            messageService.createNewMessage(message, senderName, receiverId, receiverName);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/read_message")
    public ResponseEntity<Void> readMessage(@RequestParam int threadId, @RequestParam String receiverId) {
        try {
            Jwt token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userId = token.getClaimAsString("preferred_username").toUpperCase();
            if (!userId.equals(receiverId)) {
                return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();
            }
            messageService.updateMessageIsRead(threadId, receiverId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}