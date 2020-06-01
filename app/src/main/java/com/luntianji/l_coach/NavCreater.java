package com.luntianji.l_coach;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.luntianji.data.FireStoreConnector;
import com.luntianji.l_coach.databinding.NavHeaderBinding;
import com.luntianji.l_coach.model.User;

import java.util.Arrays;
import java.util.List;

public class NavCreater extends AppCompatActivity {

    private NavHeaderBinding mBinding;
    private FirebaseAuth mAuth;
    private User currentUser;
    private boolean login;

    protected DrawerLayout d1;
    protected ActionBarDrawerToggle abdt;
    protected View navHeader;

    private static final int RC_SIGN_IN = 123;

    protected void navCreat(int id, final String page) {
        mBinding = NavHeaderBinding.inflate(getLayoutInflater());

        d1 = (DrawerLayout) findViewById(id);
        abdt = new ActionBarDrawerToggle(this, d1, R.string.Open, R.string.Close);

        abdt.setDrawerIndicatorEnabled(true);
//        abdt.setHomeAsUpIndicator(R.drawable.volley_ball);
        d1.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.bringToFront();
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

                //FireStoreConnector fireStoreConnector = new FireStoreConnector();
//                Toast.makeText(NavCreater.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                int id = item.getItemId();
                if (id == R.id.Home_nav && page != "Home") {
                    Intent intent = new Intent();
                    intent.setClass(NavCreater.this, MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.Start_Training_nav && page != "Start Training") {
                    Intent intent = new Intent();
                    intent.setClass(NavCreater.this, StartTrainingActivity.class);
                    startActivity(intent);
                } else if (id == R.id.My_Training_nav && !page.equals("My Training")) {
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (firebaseUser == null) {
                        createSignInIntent();
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(NavCreater.this, MyTrainingActivity.class);
                        startActivity(intent);
                    }

                } else if (id == R.id.Community_nav && !page.equals("Community")) {

                } else if (id == R.id.Team_nav && !page.equals("Team")) {
                    Intent intent = new Intent();
                    intent.setClass(NavCreater.this, TeamActivity.class);
                    startActivity(intent);
                } else if (id == R.id.Setting_nav && !page.equals("Setting")) {
                    Intent intent = new Intent();
                    intent.setClass(NavCreater.this, SettingActivity.class);
                    startActivity(intent);
//                    createSignInIntent();
                    //FireStoreConnector.dataUpload();
                }
                return true;
            }
        });

        // Auth.
        mAuth = FirebaseAuth.getInstance();
        navHeader = nav_view.getHeaderView(0);
        mBinding = NavHeaderBinding.bind(navHeader);
        // End auth.

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    // Auth.
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null) {
            setLogin(false);
        } else {
            setLogin(true);
        }

    }

    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.ball)      // Set logo drawable
                        .setTheme(R.style.Theme_MyApp)      // Set theme
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }

    private void createMyProfileIntent() {
        Intent intent = new Intent(this, MyProfileActivity.class);
        intent.putExtra("User", currentUser);
        startActivityForResult(intent, 2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                setLogin(true);
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    private void setLogin(boolean isLogin) {
        login = isLogin;
        if (login == true) {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            currentUser = new User(firebaseUser.getUid(), firebaseUser.getDisplayName());
            mBinding.setUser(currentUser);
            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createMyProfileIntent();
                }
            });
        } else {
            currentUser = new User("0", "登入 William Chi");
            mBinding.setUser(currentUser);
            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createSignInIntent();
                }
            });
        }
    }
}
