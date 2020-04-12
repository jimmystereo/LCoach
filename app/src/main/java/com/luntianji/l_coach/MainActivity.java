package com.luntianji.l_coach;

import android.os.Bundle;

public class MainActivity extends NavCreater {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< Updated upstream
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
=======
        navCreat(R.id.activity_main, "Home");
>>>>>>> Stashed changes
    }
}