package com.luntianji.l_coach;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luntianji.l_coach.model.Training;

import java.util.List;

import genomu.command.GetListCommand;
import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBEmcee;
import genomu.firestore_helper.DBReceiver;
import genomu.firestore_helper.HanWen;

public class StartTrainingActivity extends NavCreater {
    private  RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TrainingListAdapter trainingListAdapter;
    private int difficulty;
    private FilterFragment fragment = new FilterFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
        navCreat(R.id.activity_start_training,"Start Training");
        recyclerView = (RecyclerView) findViewById(R.id.start_trainging_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        DBReceiver receiver = new DBReceiver() {
            @Override
            public void onReceive(List receivedList) {
//                Log.d(TAG, "onReceive: ");
                trainingListAdapter = new TrainingListAdapter(receivedList);
                recyclerView.setAdapter(trainingListAdapter);
            }
        };
        registerReceiver(receiver,new IntentFilter(DBEmcee.ACTION01));
        DBCommand command = new GetListCommand(new HanWen("start_training_list"),this,Training.class);
        command.work();

    }

    public void cancelFilterFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment.dismiss();
    }

    public void openFilterFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragment.show(fragmentManager, "FilterFragment");

    }

    public void doneFilterFragment(View v) {

    }


}
