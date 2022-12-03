package com.example.cw3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cw3.databinding.ActivityCourseBinding;
import com.example.cw3.databinding.ActivityUserProfileBinding;
import com.example.cw3.viewmodels.CourseVM;
import com.example.cw3.viewmodels.UserProfileVM;

import java.util.Objects;

public class UserProfile extends AppCompatActivity {
    private UserProfileVM model;
    ActivityUserProfileBinding activityUserProfileBinding;
    EditText a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(UserProfileVM.class);
        activityUserProfileBinding = ActivityUserProfileBinding.inflate(LayoutInflater.from(this));
        setContentView(activityUserProfileBinding.getRoot());
        activityUserProfileBinding.setUsermodel(model);
        activityUserProfileBinding.setLifecycleOwner(this);
        Objects.requireNonNull(getSupportActionBar()).hide();

        activityUserProfileBinding.SaveButton.setOnClickListener(v -> {
            a = (EditText) findViewById(R.id.UserName);
            model.setUserName(a.getText().toString());
        });

    }
}