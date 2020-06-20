package com.luntianji.l_coach;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.luntianji.l_coach.databinding.ActivityMyTrainingEditBinding;
import com.luntianji.l_coach.model.Training;

import genomu.command.CreateCommand;
import genomu.command.DeleteCommand;
import genomu.command.UpdateCommand;
import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBReceiver;

import static genomu.firestore_helper.DBEmcee.ACTION01;


public class MyTrainingEditActivity extends ThemeManager {

    private static final String TAG = "AddTrainingActivity";

    // binding
    private ActivityMyTrainingEditBinding mBinding;
    // state
    private Training training;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // binding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_training_edit);
        training = (Training) getIntent().getSerializableExtra("Training");
        mBinding.setTraining(training);

        if (training.getId() != null) {
            mBinding.setEdit(false);
        } else {
            mBinding.setCreate(true);
        }

        mBinding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTraining();
            }
        });
        mBinding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTraining();
            }
        });
    }

    public void onToggleEditClick(View view) {
        if (mBinding.getEdit()) {
            save();
        }
        mBinding.setEdit(!mBinding.getEdit());
    }

    private void save() {
        if (training.getName().length() > 0) {
            if (training.getId().length() > 0) {
                updateTraining();
            } else {
                Toast.makeText(this, "id is null", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateTraining() {
        training.setUserId(userId);
        DBReceiver receiver = new DBReceiver() {

        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new UpdateCommand<>("my_training_list", this, training);
        command.work();
        setResult(RESULT_OK);
    }

    private void addTraining() {
        training.setUserId(userId);
        DBReceiver receiver = new DBReceiver() {
            @Override
            public void onReceive(Object receivedPOJO) {
                super.onReceive(receivedPOJO);
                Training newTraining = (Training) receivedPOJO;
                training = newTraining;
                mBinding.setCreate(false);
                mBinding.setEdit(false);
            }
        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new CreateCommand("my_training_list", this, training);
        command.work();
        mBinding.setCreate(false);
    }

    private void deleteTraining() {
        DBReceiver receiver = new DBReceiver() {
        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new DeleteCommand<>("my_training_list", this, training);
        command.work();
        setResult(RESULT_OK);
        finish();
    }
}