package com.luntianji.l_coach;

import android.os.Bundle;

public class StartTrainingActivity extends NavCreater {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
        navCreat(R.id.activity_start_training, "Start Training");
    }
}
