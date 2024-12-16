package journal.backend_message.Core;

import journal.backend_message.Core.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000",
        "https://journal-app-frontend.app.cloud.cbh.kth.se"})
@RestController
public class Controller {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private HAPIServiceClient hapiServiceClient;

    public Controller() {}

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getLatestMessagesInThreads(@RequestParam String id) {
        try {
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
            User sender = userServiceClient.getUserById(newMessage.getSenderId());

            String receiverIdentifier = newMessage.getReceiverId();
            if (receiverIdentifier == null && sender.getAuthority().equals(Authority.Patient)) {
                receiverIdentifier = hapiServiceClient.getGeneralPractitionerByIdentifier(newMessage.getSenderId());
            }
            User receiver = userServiceClient.getUserById(receiverIdentifier);
            if (receiver == null)
                ResponseEntity.badRequest().build();

            messageService.createNewMessage(newMessage, sender, receiver);
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