package com.luntianji.l_coach;

import android.app.Activity;
import android.content.Intent;
import android.service.voice.VoiceInteractionSession;
import android.text.Layout;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.luntianji.data_matters.FireStoreConnector;

public class NavCreater extends AppCompatActivity {
    protected DrawerLayout d1;
    protected ActionBarDrawerToggle abdt;

    protected void navCreat(int id, final String page) {
        d1 = (DrawerLayout) findViewById(id);
        abdt = new ActionBarDrawerToggle(this, d1, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        d1.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        switch (page) {
            case "Home":
                nav_view.setCheckedItem(R.id.Home_nav);
                break;
            case "Start Training":
                nav_view.setCheckedItem(R.id.Start_Training_nav);
                break;
            case "My Training":
                nav_view.setCheckedItem(R.id.My_Training_nav);
                break;
            case "Team":
                nav_view.setCheckedItem(R.id.Team_nav);

                break;
            case "Setting":
                nav_view.setCheckedItem(R.id.Setting_nav);
                break;
        }
        nav_view.bringToFront();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(NavCreater.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                int id = item.getItemId();
                if (id == R.id.Home_nav && page != "Home") {
                    Intent intent = new Intent();
                    intent.setClass(NavCreater.this, MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.Start_Training_nav && !page.equals("Start Training")) {
                    Intent intent = new Intent();
                    intent.setClass(NavCreater.this, StartTrainingActivity.class);
                    startActivity(intent);
                } else if (id == R.id.My_Training_nav && !page.equals("My Training")) {

                } else if (id == R.id.Team_nav && !page.equals("Team")) {

                } else if (id == R.id.Setting_nav && !page.equals("Setting")) {
                    FireStoreConnector.dataUpload();
                }
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

}
