package com.luntianji.l_coach.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Teammate implements Serializable {
    private String id;
    private String name;
    private String role;
    private String number;
    private String info;
    private String userId;

    public Teammate(String id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public Teammate() {

    }

    public Teammate(String name, String role, String number, String info, String userId) {
        this.name = name;
        this.role = role;
        this.number = number;
        this.info = info;
        this.userId = userId;
    }



    public Teammate(String id, String name, String role, String number, String info, String userId) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.number = number;
        this.info = info;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getId();
    }

    public String getInfo() {
        return info;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
