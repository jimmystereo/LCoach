package com.luntianji.l_coach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.luntianji.l_coach.databinding.ActivityMyProfileBinding;
import com.luntianji.l_coach.model.User;
import com.squareup.picasso.Picasso;

public class MyProfileActivity extends AppCompatActivity {

    // binding
    private ActivityMyProfileBinding mBinding;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // binding
        if (SettingActivity.newTheme) {
            setTheme(R.style.RedTheme);
        } else {
            setTheme(R.style.Theme_MyApp);
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile);
        user = (User) getIntent().getSerializableExtra("User");
        mBinding.setUser(user);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Picasso.get()
                .load(firebaseUser.getPhotoUrl())
                .resize(160, 160)
                .centerCrop()
                .into(mBinding.imageView4);

    }

    public void logout(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                    }
                });
    }


}
