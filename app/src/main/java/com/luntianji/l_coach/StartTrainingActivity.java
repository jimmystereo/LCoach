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

public class StartTrainingActivity extends NavCreater implements AdapterView.OnItemSelectedListener {
    private  RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TrainingListAdapter trainingListAdapter;
    private int difficulty;
    private boolean filterOpen = false;
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

        Spinner spinner = (Spinner) findViewById(R.id.filter_type);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.string_filter_type, R.layout.filter_fold);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.filter_view);
// Apply the adapter to the spinner

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }
    public void openFilterFragment(View view){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FilterFragment fragment = new FilterFragment();
        if(!filterOpen){
            fragmentTransaction.add(R.id.activity_start_training,fragment);
            filterOpen = true;}
        else {
            fragmentTransaction.remove(fragment);
            filterOpen = false;}
        fragmentTransaction.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(StartTrainingActivity.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
