package com.luntianji.l_coach.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Training implements Serializable, Parcelable {


    private String id;
    private String amount_ball;
    private String amount_people;
    private String ball_per_people;
    private String difficulty;
    private String least_people;
    private String name;
    private String number;
    private String other;
    private String type;
//    public final static Training dummy = new Training("至少一顆球","兩人以上","0.5","中","2","從接球開始的扣球","116","球場","扣球");
//    public static List<Training> dummyList= new ArrayList<Training>();
    public Training(String amount_ball, String amount_people, String ball_per_people,
                    String difficulty, String least_people, String name,
                    String number, String other, String type) {
        this.amount_ball = amount_ball;
        this.amount_people = amount_people;
        this.ball_per_people = ball_per_people;
        this.difficulty = difficulty;
        this.least_people = least_people;
        this.name = name;
        this.number = number;
        this.other = other;
        this.type = type;
    }

    public Training() {
    }

    protected Training(Parcel in) {
        id = in.readString();
        amount_ball = in.readString();
        amount_people = in.readString();
        ball_per_people = in.readString();
        difficulty = in.readString();
        least_people = in.readString();
        name = in.readString();
        number = in.readString();
        other = in.readString();
        type = in.readString();
    }

    public static final Creator<Training> CREATOR = new Creator<Training>() {
        @Override
        public Training createFromParcel(Parcel in) {
            return new Training(in);
        }

        @Override
        public Training[] newArray(int size) {
            return new Training[size];
        }
    };

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

    public String getBall_per_people() {
        return ball_per_people;
    }

    public void setBall_per_people(String ball_per_people) {
        this.ball_per_people = ball_per_people;
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

    @NonNull
    @Override
    public String toString() {
        return this.getId();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(amount_ball);
        dest.writeString(amount_people);
        dest.writeString(ball_per_people);
        dest.writeString(difficulty);
        dest.writeString(least_people);
        dest.writeString(name);
        dest.writeString(number);
        dest.writeString(other);
        dest.writeString(type);
    }
}
