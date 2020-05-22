package com.luntianji.l_coach.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Teammate implements Serializable {
    private String id;
    private String name;
    private String role;
    private String number;
    private String info;

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

    public Teammate(String name, String role, String number, String info) {
        this.name = name;
        this.role = role;
        this.number = number;
        this.info = info;
    }

    public Teammate(String id, String name, String role, String number, String info) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.number = number;
        this.info = info;
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
}
