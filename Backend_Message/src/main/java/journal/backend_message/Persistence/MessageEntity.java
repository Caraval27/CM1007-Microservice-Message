package journal.backend_message.Persistence;

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
    @ManyToOne
    @JoinColumn(name = "sender", referencedColumnName = "id", nullable = false)
    private UserEntity sender;
    @ManyToOne
    @JoinColumn(name = "receiver", referencedColumnName = "id", nullable = false)
    private UserEntity receiver;

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

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean answered) {
        this.read = answered;
    }

    public MessageEntity() {
    }

    public MessageEntity(int threadId, String title, String body, LocalDateTime date, UserEntity sender, UserEntity receiver, boolean read) {
        this.threadId = threadId;
        this.title = title;
        this.body = body;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
        this.read = read;
    }
}