package com.luntianji.l_coach;

import android.content.IntentFilter;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.luntianji.l_coach.model.Training;

import genomu.command.CreateCommand;
import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBReceiver;

import static genomu.firestore_helper.DBEmcee.ACTION01;

public class DetailManager extends NavCreater {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TrainingDetailFragment.background = true;
    }

    public void hideAddButton() {
        //在myTraining中實作隱藏按鈕
    }

    public void closeDetail(View view) {
        TrainingDetailFragment.closeDetail();
    }

    public void comfirmDetail(View view) {
        TrainingDetailFragment.comfirmDetail();
    }

    public void addToMyTraining(View view) {
        // auth
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            createSignInIntent();
            return;
        }
        String userId = firebaseUser.getUid();

        //pojo
        Training training = DailySelectedAdapter.getDetailFragment().training;
        // need userId
        training.setUserId(userId);

        DBReceiver receiver = new DBReceiver() {
        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new CreateCommand("my_training_list", this, training);
        command.work();
    }
}
