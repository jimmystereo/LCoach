package com.luntianji.l_coach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class StartTrainingActivity extends NavCreater {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
        navCreat(R.id.activity_start_training, "Start Training");
    }
    public void easyList(View view){
        Intent intent = new Intent();
        intent.setClass(StartTrainingActivity.this, StartTrainingListActivity.class);
        startActivity(intent);
    }
}
