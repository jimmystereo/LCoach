package com.luntianji.l_coach;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ThemeManager extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SettingActivity.newTheme) {
            setTheme(R.style.RedTheme);
        } else {
            setTheme(R.style.Theme_MyApp);
        }
    }
}
