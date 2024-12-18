package journal.lab3_message.Persistence;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_message")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    private int messageId;
    @Column(name = "thread_id", nullable = false)
    private int threadId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "body", nullable = false)
    private String body;
    @Column(name = "date", nullable = false)
    private LocalDateTime date;
    @Column(name = "senderId", nullable = false)
    private String senderId;
    @Column(name = "senderName", nullable = false)
    private String senderName;
    @Column(name = "receiverId", nullable = false)
    private String receiverId;
    @Column(name = "receiverName", nullable = false)
    private String receiverName;
    @Column(name = "is_read", nullable = false)
    private boolean read;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean answered) {
        this.read = answered;
    }

    public MessageEntity() {
    }

    public MessageEntity(int threadId, String title, String body, LocalDateTime date, String senderId, String receiverId, String senderName, String receiverName, boolean read) {
        this.threadId = threadId;
        this.title = title;
        this.body = body;
        this.date = date;
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.read = read;
    }
}