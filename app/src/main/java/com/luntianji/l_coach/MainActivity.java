package com.luntianji.l_coach;

import android.os.Bundle;

public class MainActivity extends NavCreater {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navCreat(R.id.activity_main, "Home");
    }
}