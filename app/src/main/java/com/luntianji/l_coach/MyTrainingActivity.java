package com.luntianji.l_coach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



import com.luntianji.l_coach.model.Training;


public class MyTrainingActivity extends NavCreater
        implements MyTrainingFragment.OnListFragmentInteractionListener {

    private static final String TAG = MyTrainingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_training);
    }


    @Override
    public void onClick(Training training) {
        Intent intent = new Intent(this, MyTrainingEditActivity.class);
        intent.putExtra("UpdateTrainingId", training.getId());
        intent.putExtra("UpdateTrainingName", training.getName());
        intent.putExtra("UpdateTrainingDifficulty", training.getDifficulty());
        intent.putExtra("UpdateTrainingOther", training.getOther());
        startActivityForResult(intent, 2);

    }

    /**
     * Called when the user taps the floating action bar
     */
    public void startMyTrainingEditActivity(View view) {
        Intent intent = new Intent(this, MyTrainingEditActivity.class);
        startActivityForResult(intent, 2);
    }

}
