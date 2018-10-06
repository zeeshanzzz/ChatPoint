package com.example.khan.chatpoint.dataModels;

public class Objectchat {
    private String chatId, name;

    public String getChatId() {
        return chatId;
    }

    public String getFriendName() {
        return name;
    }

    public void setFriendName(String friendName) {
        name = friendName;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Objectchat(String chatId) {
        this.chatId = chatId;

    }
}
