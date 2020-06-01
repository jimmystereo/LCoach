package com.luntianji.l_coach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.luntianji.l_coach.databinding.ActivityMyProfileBinding;
import com.luntianji.l_coach.model.User;

public class MyProfileActivity extends AppCompatActivity {

    private ActivityMyProfileBinding mBinding;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SettingActivity.newTheme){setTheme(R.style.RedTheme);}
        else{setTheme(R.style.Theme_MyApp);}
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile);
        user = (User) getIntent().getSerializableExtra("User");
        mBinding.setUser(user);
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
