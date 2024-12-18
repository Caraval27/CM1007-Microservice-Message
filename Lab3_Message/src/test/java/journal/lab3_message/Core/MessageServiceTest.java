package journal.lab3_message.Core;

import journal.lab3_message.Core.Model.CreateMessage;
import journal.lab3_message.Core.Model.Message;
import journal.lab3_message.Persistence.IMessageRepository;
import journal.lab3_message.Persistence.MessageEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    @Mock
    private IMessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLatestMessagesByUserInThread() {
        String userId = "user123";
        MessageEntity mockEntity1 = new MessageEntity(1, "Title1", "Body1", LocalDateTime.now(), userId, "SenderName1", "ReceiverId1", "ReceiverName1", false);
        MessageEntity mockEntity2 = new MessageEntity(2, "Title2", "Body2", LocalDateTime.now(), userId, "SenderName2", "ReceiverId2", "ReceiverName2", false);
        when(messageRepository.findLatestMessagesByUserInThread(userId)).thenReturn(Arrays.asList(mockEntity1, mockEntity2));

        List<Message> messages = messageService.getLatestMessagesByUserInThread(userId);

        assertEquals(2, messages.size());
        assertEquals("Title1", messages.get(0).getTitle());
        verify(messageRepository, times(1)).findLatestMessagesByUserInThread(userId);
    }

    @Test
    void testGetMessagesInThread() {
        int threadId = 1;
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        MessageEntity mockEntity = new MessageEntity(threadId, "Title", "Body", LocalDateTime.now(), "senderId", "senderName", "receiverId", "receiverName", false);
        when(messageRepository.findByThreadId(threadId, sort)).thenReturn(List.of(mockEntity));

        List<Message> messages = messageService.getMessagesInThread(threadId);

        assertEquals(threadId, messages.size());
        assertEquals("Title", messages.get(0).getTitle());
        verify(messageRepository, times(1)).findByThreadId(threadId, sort);
    }

    @Test
    void testCreateNewMessage() {
        CreateMessage createMessage = new CreateMessage();
        String senderName = "SenderName";
        String receiverId = "ReceiverId";
        String receiverName = "ReceiverName";
        when(messageRepository.findMaxThreadId()).thenReturn(5);

        messageService.createNewMessage(createMessage, senderName, receiverId, receiverName);

        verify(messageRepository, times(1)).save(any(MessageEntity.class));
    }

    @Test
    void testUpdateMessageIsRead() {
        String threadId = "1";
        String receiverId = "receiverId";
        when(messageRepository.updateMessageIsRead(threadId, receiverId)).thenReturn(1);

        messageService.updateMessageIsRead(threadId, receiverId);

        verify(messageRepository, times(1)).updateMessageIsRead(threadId, receiverId);
    }
}