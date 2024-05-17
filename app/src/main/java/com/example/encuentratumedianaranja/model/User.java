package com.example.encuentratumedianaranja.model;

import java.io.Serializable;

public class User implements Serializable {
    private String uid;
    private String name;
    private String profileImageUrl;
    private String description;

    public User() {
        // Constructor vacío requerido para Firebase
    }

    public User(String uid, String name, String profileImageUrl, String description) {
        this.uid = uid;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
