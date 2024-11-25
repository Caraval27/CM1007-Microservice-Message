package journal.backend_message.Core.Model;

import journal.backend_message.Persistence.MessageEntity;

import java.time.LocalDateTime;

public class Message {
    private int messageId;
    private int threadId;
    private String title;
    private String body;
    private LocalDateTime date;
    private User sender;
    private User receiver;
    private boolean read;

    public Message(MessageEntity messageEntity) {
        messageId = messageEntity.getMessageId();
        threadId = messageEntity.getThreadId();
        title = messageEntity.getTitle();
        body = messageEntity.getBody();
        date = messageEntity.getDate();
        sender = new User(messageEntity.getSender());
        receiver = new User(messageEntity.getReceiver());
        read = messageEntity.isRead();
    }

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

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", threadId=" + threadId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", date=" + date +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", answered=" + read +
                '}';
    }
}