package com.example.cw3.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cw3.databinding.ActivityUserSelectedBinding;
import com.example.cw3.viewmodels.UserSelectedVM;

import java.util.Objects;

public class UserSelected extends AppCompatActivity {
    private UserSelectedVM userSelectedVM;
    private ActivityUserSelectedBinding activityUserSelectedBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userSelectedVM = new ViewModelProvider(this).get(UserSelectedVM.class);
        activityUserSelectedBinding = ActivityUserSelectedBinding.inflate(LayoutInflater.from(this));
        setContentView(activityUserSelectedBinding.getRoot());
        activityUserSelectedBinding.setModel(userSelectedVM);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // not good practice but short on time
        userSelectedVM.addUserName(getIntent().getExtras().getString("userName"));
        userSelectedVM.addUserAge(getIntent().getExtras().getInt("userAge"));
        userSelectedVM.addUserWeight(getIntent().getExtras().getDouble("userWeight"));
        userSelectedVM.addUserSelected(getIntent().getExtras().getBoolean("userSelected"));

        activityUserSelectedBinding.DeleteButton.setOnClickListener(v -> {
            userSelectedVM.DeleteProfile();
            finish();
        });

        activityUserSelectedBinding.SelectButton.setOnClickListener(v -> {
            // deselect all users then select only one.
            userSelectedVM.RemoveAllSelectedProfile();
            userSelectedVM.selectProfile();
            finish();
        });
    }
}