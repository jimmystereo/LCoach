package com.luntianji.l_coach;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.luntianji.l_coach.model.Training;

import genomu.command.DeleteCommand;
import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBReceiver;

import static com.luntianji.l_coach.TrainingDetailFragment.opened;
import static genomu.firestore_helper.DBEmcee.ACTION01;


public class MyTrainingActivity extends DetailManager
        implements MyTrainingFragment.OnListFragmentInteractionListener {

    static TrainingDetailFragment detailFragment;
    private static final String TAG = MyTrainingActivity.class.getSimpleName();
    private Training selectedTraining = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_training);
        navCreat(R.id.activity_my_training, "My Training");
        opened = false;
    }

    // TrainingDetailFragment
    @Override
    public void onClick(Training training) {
        if (!opened) {
            selectedTraining = training;
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
        if (selectedTraining != null) {
            deleteTraining(selectedTraining.getId());
            finish();
            startActivity(getIntent());
        }
    }

    private void deleteTraining(String id) {
        // pojo
        Training training = new Training();
        training.setId(id);

        DBReceiver receiver = new DBReceiver() {
        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new DeleteCommand<>("my_training_list", this, training);
        command.work();
    }
}