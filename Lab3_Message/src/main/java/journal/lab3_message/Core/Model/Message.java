package journal.lab3_message.Core.Model;

import journal.lab3_message.Persistence.MessageEntity;

import java.time.LocalDateTime;

public class Message {
    private int messageId;
    private int threadId;
    private String title;
    private String body;
    private LocalDateTime date;
    private String senderId;
    private String senderName;
    private String receiverId;
    private String receiverName;
    private boolean read;

    public Message(MessageEntity messageEntity) {
        messageId = messageEntity.getMessageId();
        threadId = messageEntity.getThreadId();
        title = messageEntity.getTitle();
        body = messageEntity.getBody();
        date = messageEntity.getDate();
        senderId = messageEntity.getSenderId();
        senderName = messageEntity.getSenderName();
        receiverId = messageEntity.getReceiverId();
        receiverName = messageEntity.getReceiverName();
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
                ", senderId='" + senderId + '\'' +
                ", senderName='" + senderName + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", read=" + read +
                '}';
    }
}