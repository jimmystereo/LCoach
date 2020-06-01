package com.luntianji.l_coach;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.luntianji.l_coach.model.Training;

import java.util.List;
import java.util.Map;

import genomu.command.GetListCommand;
import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBEmcee;
import genomu.firestore_helper.DBReceiver;

public class SettingActivity extends NavCreater {
    static boolean newTheme = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(newTheme){setTheme(R.style.RedTheme); }
        setContentView(R.layout.activity_setting);
        navCreat(R.id.activity_setting, "Setting");
    }
    public void setT(View view){
        if(newTheme){newTheme=false;}
        else{newTheme = true;}
        recreate();
    }
}
