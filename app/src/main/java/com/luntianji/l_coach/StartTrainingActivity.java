package com.luntianji.l_coach;

import android.os.Bundle;

import com.luntianji.data_matters.FireStoreConnector;
import com.luntianji.data_matters.StartTrainingList;

public class StartTrainingActivity extends NavCreater {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
        navCreat(R.id.activity_start_training, "Start Training");
        StartTrainingList startTrainingList = new StartTrainingList();
        FireStoreConnector fireStoreConnector = new FireStoreConnector();
        fireStoreConnector.pushDataToFireStore("start_training_list",startTrainingList.setStart_training_list());
    }
}
