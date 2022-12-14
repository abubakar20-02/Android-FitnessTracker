package com.example.cw3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.cw3.Database.UserProfile;
import com.example.cw3.databinding.ActivityUserSelectedBinding;
import com.example.cw3.viewmodels.UserSelectedVM;

import java.util.Objects;

public class UserSelected extends AppCompatActivity {
    private UserSelectedVM model;
    private ActivityUserSelectedBinding activityUserSelectedBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(UserSelectedVM.class);
        activityUserSelectedBinding = ActivityUserSelectedBinding.inflate(LayoutInflater.from(this));
//        setContentView(R.layout.activity_user_selected);
        setContentView(activityUserSelectedBinding.getRoot());
        activityUserSelectedBinding.setModel(model);
        Objects.requireNonNull(getSupportActionBar()).hide();
        model.addUserName(getIntent().getExtras().getString("userName"));
        model.addUserAge(getIntent().getExtras().getInt("userAge"));
        model.addUserWeight(getIntent().getExtras().getDouble("userWeight"));
        model.addUserSelected(getIntent().getExtras().getBoolean("userSelected"));

        //When the start button is pressed go to it's activity
        activityUserSelectedBinding.DeleteButton.setOnClickListener(v -> {
            model.DeleteProfile();
            finish();
        });

        //When the start button is pressed go to it's activity
        activityUserSelectedBinding.SelectButton.setOnClickListener(v -> {
            // set all selected to false then set the one clicked to true
            model.RemoveAllSelectedProfile();
            model.selectProfile();
            finish();
        });
    }
}