package journal.backend_message.Core;

import journal.backend_message.Core.Model.CreateMessage;
import journal.backend_message.Core.Model.Message;
import journal.backend_message.Core.Model.User;
import journal.backend_message.Persistence.IMessageRepository;
import journal.backend_message.Persistence.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    IMessageRepository messageRepository;

    public List<Message> getLatestMessagesByUserInThread(String id) {
        List<MessageEntity> messageEntities = messageRepository.findLatestMessagesByUserInThread(id);
        List<Message> messages = new ArrayList<>();
        for (MessageEntity messageEntity : messageEntities) {
            messages.add(new Message(messageEntity));
        }
        return messages;
    }

    public List<Message> getMessagesInThread(int threadId) {
        List<MessageEntity> messageEntities = messageRepository.findByThreadId(threadId, Sort.by(Sort.Direction.DESC, "date"));
        List<Message> messages = new ArrayList<>();
        for (MessageEntity messageEntity : messageEntities) {
            messages.add(new Message(messageEntity));
        }
        return messages;
    }

    public void createNewMessage(CreateMessage message, User sender, User receiver) {
        int threadId = message.getThreadId();
        if (threadId == -1) {
            threadId = messageRepository.findMaxThreadId() + 1;
        }
        LocalDateTime date = LocalDateTime.now();
        boolean answered = false;
        MessageEntity messageEntity = new MessageEntity(threadId, message.getTitle(), message.getBody(),
                date, sender.getEntity(), receiver.getEntity(), answered);
        messageRepository.save(messageEntity);
    }

    public void updateMessageIsRead(String threadId, String receiverId) {
        messageRepository.updateMessageIsRead(threadId, receiverId);
    }
}
