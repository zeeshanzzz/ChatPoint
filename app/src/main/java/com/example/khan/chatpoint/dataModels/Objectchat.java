package com.example.khan.chatpoint.dataModels;

public class Objectchat {
    private String chatId, FriendName;

    public String getChatId() {
        return chatId;
    }

    public String getFriendName() {
        return FriendName;
    }

    public void setFriendName(String friendName) {
        FriendName = friendName;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Objectchat(String chatId) {
        this.chatId = chatId;
    }
}
