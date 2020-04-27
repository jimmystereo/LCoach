package com.luntianji.l_coach;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public class StartTrainingActivity extends NavCreater {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
        navCreat(R.id.activity_start_training, "Start Training");
        String[] difficulty = new String[]{"低","中","高"};
//        ListView listView = (ListView) findViewById(R.id.choose_difficulty);
//        ListAdapter adapter = new ArrayAdapter<>(this,R.layout.activity_start_training,difficulty);
//        listView.setAdapter(adapter);

    }
}
