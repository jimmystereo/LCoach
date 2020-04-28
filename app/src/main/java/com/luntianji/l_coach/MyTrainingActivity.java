package com.luntianji.l_coach;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luntianji.data.Training;

public class MyTrainingActivity extends NavCreater {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TrainingListAdapter trainingListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_training);
        navCreat(R.id.activity_my_training,"My Training");
        recyclerView = (RecyclerView) findViewById(R.id.my_trainging_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        Training.dummyList.add(Training.dummy);
        trainingListAdapter = new TrainingListAdapter(Training.dummyList);
        recyclerView.setAdapter(trainingListAdapter);
    }
}
