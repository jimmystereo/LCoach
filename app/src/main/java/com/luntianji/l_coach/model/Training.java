package com.luntianji.l_coach.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Training implements Serializable {
    private String detail;
    private String id;
    private String amount_ball;
    private String amount_people;
    private String ball_per_person;
    private String difficulty;
    private String least_people;
    private String name;
    private String number;
    private String other;
    private String type;
    private String userId;

    //    public final static Training dummy = new Training("至少一顆球","兩人以上","0.5","中","2","從接球開始的扣球","116","球場","扣球");
//    public static List<Training> dummyList= new ArrayList<Training>();
    public Training(String amount_ball, String amount_people, String ball_per_person,
                    String detail,
                    String difficulty, String least_people, String name,
                    String number, String other, String type) {
        this.detail = detail;
        this.amount_ball = amount_ball;
        this.amount_people = amount_people;
        this.ball_per_person = ball_per_person;
        this.difficulty = difficulty;
        this.least_people = least_people;
        this.name = name;
        this.number = number;
        this.other = other;
        this.type = type;
    }

    public Training(String amount_ball, String amount_people, String ball_per_person,
                    String difficulty, String least_people, String name,
                    String number, String other, String type) {
        this.amount_ball = amount_ball;
        this.amount_people = amount_people;
        this.ball_per_person = ball_per_person;
        this.difficulty = difficulty;
        this.least_people = least_people;
        this.name = name;
        this.number = number;
        this.other = other;
        this.type = type;
    }

    public Training() {
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount_ball() {
        return amount_ball;
    }

    public void setAmount_ball(String amount_ball) {
        this.amount_ball = amount_ball;
    }

    public String getAmount_people() {
        return amount_people;
    }

    public void setAmount_people(String amount_people) {
        this.amount_people = amount_people;
    }

    public String getBall_per_person() {
        return ball_per_person;
    }

    public void setBall_per_person(String ball_per_person) {
        this.ball_per_person = ball_per_person;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getLeast_people() {
        return least_people;
    }

    public void setLeast_people(String least_people) {
        this.least_people = least_people;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getId();
    }
}
