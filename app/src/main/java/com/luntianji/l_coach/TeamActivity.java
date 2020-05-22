package com.luntianji.l_coach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class TeamActivity extends NavCreater {

    MaterialCardView myTeammateCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        navCreat(R.id.activity_team,"Team");

        myTeammateCardView = findViewById(R.id.cardview_my_teammate);
        myTeammateCardView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // item clicked
                startMyTeammateActivity(myTeammateCardView);
            }
        });
    }

    /**
     * Called when the user taps the cardView
     */
    public void startMyTeammateActivity(View view) {
        Intent intent = new Intent(this, MyTeammateActivity.class);
        startActivity(intent);
    }
}
