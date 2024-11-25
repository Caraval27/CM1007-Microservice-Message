package journal.backend_message.Core;

import journal.backend_message.Core.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class Controller {
    @Autowired
    private MessageService messageService;

    public Controller() {}

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getLatestMessagesInThreads(@RequestParam String id) {
        try {
            return ResponseEntity.ok(messageService.getLatestMessagesByUserInThread(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/messages_in_thread")
    public ResponseEntity<List<Message>> getMessagesInThread(@RequestParam int threadId) {
        try {
            return ResponseEntity.ok(messageService.getMessagesInThread(threadId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create_message")
    public ResponseEntity<Void> createNewMessage(@RequestBody CreateMessage newMessage) {
        try {
            User sender = userService.getUserById(newMessage.getSenderId());
            String receiverIdentifier = newMessage.getReceiverId();
            if (receiverIdentifier == null && sender.getAuthority().equals(Authority.Patient)) {
                receiverIdentifier = hapiService.getGeneralPractitionerIdentifierByPatientIdentifier(newMessage.getSenderId());
            }
            User receiver = userService.getUserById(receiverIdentifier);
            if (receiver == null)
                ResponseEntity.badRequest().build();
            messageService.createNewMessage(newMessage, sender, receiver);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/read_message")
    public ResponseEntity<Void> readMessage(@RequestParam String threadId, @RequestParam String receiverId) {
        try {
            messageService.updateMessageIsRead(threadId, receiverId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}