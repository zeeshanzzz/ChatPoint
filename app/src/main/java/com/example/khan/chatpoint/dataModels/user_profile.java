package com.example.khan.chatpoint.dataModels;

public class user_profile {
    String image;
    String name;
    String status;

    public user_profile() {
    }

    public user_profile(String image, String name, String status) {
        this.image = image;
        this.name = name;
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
