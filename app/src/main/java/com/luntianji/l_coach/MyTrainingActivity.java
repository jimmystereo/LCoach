package com.luntianji.l_coach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.luntianji.l_coach.model.Training;

import static com.luntianji.l_coach.TrainingDetailFragment.opened;


public class MyTrainingActivity extends DetailManager
        implements MyTrainingFragment.OnListFragmentInteractionListener {

    static TrainingDetailFragment detailFragment;
    private static final String TAG = MyTrainingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_training);
        navCreat(R.id.activity_my_training, "My Training");
    }

    // TrainingDetailFragment
    @Override
    public void onClick(Training training) {
        if (!opened) {

            detailFragment = new TrainingDetailFragment(training, this, 2);

            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.animation_open_fragment, R.anim.animation_close_fragment);
            fragmentTransaction.add(R.id.my_training_constraint, detailFragment);
            this.setTitle("訓練內容");
            fragmentTransaction.commit();
            opened = true;
        }
    }


    // End TrainingDetailFragment

    @Override
    public boolean onLongClick(Training training) {
        Intent intent = new Intent(this, MyTrainingEditActivity.class);
        intent.putExtra("UpdateTrainingId", training.getId());
        intent.putExtra("UpdateTrainingName", training.getName());
        intent.putExtra("UpdateTrainingDifficulty", training.getDifficulty());
        intent.putExtra("UpdateTrainingOther", training.getOther());
        startActivityForResult(intent, 2);
        return true;
    }

    /**
     * Called when the user taps the floating action bar
     */
    public void startMyTrainingEditActivity(View view) {
        Intent intent = new Intent(this, MyTrainingEditActivity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    public void addToMyTraining(View view) {
        super.addToMyTraining(view);
        //TODO 刪除這筆資料
    }
}