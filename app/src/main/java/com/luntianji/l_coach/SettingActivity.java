package com.luntianji.l_coach;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
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
    AppCompatButton textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(newTheme){setTheme(R.style.RedTheme);}
        else{setTheme(R.style.Theme_MyApp);}
        setContentView(R.layout.activity_setting);
        textView = findViewById(R.id.buttom_setTheme);
        if(newTheme){textView.setText("切換主題 (現在主題: 火紅)");}
        else{textView.setText("切換主題 (現在主題: 夜店)");}
        navCreat(R.id.activity_setting, "Setting");
    }
    public void setT(View view){
        if(newTheme){newTheme=false;
        }
        else{newTheme = true;}
        recreate();
    }
}
