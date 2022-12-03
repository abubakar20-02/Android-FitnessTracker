package com.example.cw3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.cw3.databinding.ActivityCourseBinding;
import com.example.cw3.databinding.ActivityUserProfileBinding;
import com.example.cw3.viewmodels.CourseVM;
import com.example.cw3.viewmodels.UserProfileVM;

import java.util.Objects;

public class UserProfile extends AppCompatActivity {
    private UserProfileVM model;
    ActivityUserProfileBinding activityUserProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(UserProfileVM.class);
        activityUserProfileBinding = ActivityUserProfileBinding.inflate(LayoutInflater.from(this));
        setContentView(activityUserProfileBinding.getRoot());
        activityUserProfileBinding.setUsermodel(model);
        activityUserProfileBinding.setLifecycleOwner(this);
        Objects.requireNonNull(getSupportActionBar()).hide();

        model.setUserName("abu");
    }
}