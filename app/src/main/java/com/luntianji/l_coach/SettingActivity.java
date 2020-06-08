package com.luntianji.l_coach;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

public class SettingActivity extends NavCreater {
    public static boolean newTheme = false;
    AppCompatButton textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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
