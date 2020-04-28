package com.luntianji.l_coach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class TeamActivity extends NavCreater {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        navCreat(R.id.activity_team,"Team");
    }

    /**
     * Called when the user taps the cardView
     */
    public void startMyTeammateActivity(View view) {
        Intent intent = new Intent(this, MyTeammateActivity.class);
        startActivity(intent);
    }
}
