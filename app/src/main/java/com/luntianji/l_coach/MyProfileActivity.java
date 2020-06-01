package com.luntianji.l_coach;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.luntianji.l_coach.databinding.ActivityMyProfileBinding;
import com.luntianji.l_coach.model.User;

public class MyProfileActivity extends AppCompatActivity {

    private ActivityMyProfileBinding mBinding;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        mBinding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        user = (User) getIntent().getSerializableExtra("User");
        mBinding.setUser(user);
    }
}
