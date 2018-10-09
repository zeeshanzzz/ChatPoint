package com.example.khan.chatpoint.dataModels;

import java.util.ArrayList;

public class MessageObject {
    String messageId,senderId,message,date;
    ArrayList<String> stringArrayList=new ArrayList<>();

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

    public ArrayList<String> getStringArrayList() {
        return stringArrayList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStringArrayList(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }

    public MessageObject(String messageId, String message,ArrayList<String> stringArrayList,String date) {
        this.messageId = messageId;
        this.message = message;
        this.stringArrayList=stringArrayList;
        this.date=date;
    }

    public MessageObject(String messageId, String senderId, String message) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.message = message;


    }
}
