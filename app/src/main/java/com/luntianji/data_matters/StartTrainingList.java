package com.luntianji.data_matters;

import java.util.HashMap;
import java.util.Map;

public class StartTrainingList {
    private String type;
    private String name;
    private String difficulty;
    private int least_people;
    private float ball_per_person;
    private String other;
    private int id;

    public StartTrainingList(){}
    public StartTrainingList(String type, String name, String difficulty, int least_people, float ball_per_person,String other,int id){
        this.type = type;
        this.name = name;
        this.difficulty = difficulty;
        this.least_people = least_people;
        this.ball_per_person = ball_per_person;
        this.other = other;
        this.id = id;
    }
    public Map<String,Object> setStart_training_list(){
        Map<String,Object> start_training_list = new HashMap<>();
        start_training_list.put("type","整合訓練");
        start_training_list.put("name","整合訓練");
        start_training_list.put("difficulty","整合訓練");
        start_training_list.put("least_people","整合訓練");
        start_training_list.put("ball_per_people","整合訓練");
        start_training_list.put("other","整合訓練");
        start_training_list.put("id","整合訓練");
        return start_training_list;
    }
}
