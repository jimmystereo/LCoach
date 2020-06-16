package com.luntianji.l_coach.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class User implements Serializable {

    private String uid;
    private String name;

    public User(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getUid();
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
}
