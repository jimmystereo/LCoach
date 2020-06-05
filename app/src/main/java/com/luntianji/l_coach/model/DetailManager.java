package com.luntianji.l_coach.model;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.luntianji.l_coach.DailySelectedAdapter;
import com.luntianji.l_coach.NavCreater;
import com.luntianji.l_coach.TrainingDetailFragment;

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
        TrainingDetailFragment.resetDetail();
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
