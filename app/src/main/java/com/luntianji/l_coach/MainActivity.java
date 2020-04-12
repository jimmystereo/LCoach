package com.luntianji.l_coach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout d1;
    private ActionBarDrawerToggle abdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        d1 = (DrawerLayout) findViewById(R.id.navigation_menu);
        abdt = new ActionBarDrawerToggle(this,d1,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        d1.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.Home_nav){
                    Toast.makeText(MainActivity.this,"Home",Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.Start_Training_nav){
                    Toast.makeText(MainActivity.this,"Start Training",Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.My_Training_nav){
                    Toast.makeText(MainActivity.this,"My Training",Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.Team_nav){
                    Toast.makeText(MainActivity.this,"Team",Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.Setting){
                    Toast.makeText(MainActivity.this,"Setting",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return abdt.onOptionsItemSelected(item)||super.onOptionsItemSelected(item);
    }

}
