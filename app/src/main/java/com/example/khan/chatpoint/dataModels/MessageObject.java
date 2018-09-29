package com.example.khan.chatpoint.dataModels;

public class MessageObject {
    String messageId,senderId,message;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageObject(String message) {
        this.message = message;
    }

    public MessageObject(String messageId, String message) {
        this.messageId = messageId;
        this.message = message;
    }

    public MessageObject(String messageId, String senderId, String message) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.message = message;

    }
}
