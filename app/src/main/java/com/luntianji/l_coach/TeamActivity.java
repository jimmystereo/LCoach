package com.luntianji.l_coach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class TeamActivity extends NavCreater {

    private static final int RC_SIGN_IN = 123;
    MaterialCardView myTeammateCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SettingActivity.newTheme){setTheme(R.style.RedTheme);}
        else{setTheme(R.style.Theme_MyApp);}
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
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            createSignInIntent();
            return;
        }

        Intent intent = new Intent(this, MyTeammateActivity.class);
        startActivity(intent);
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
}
