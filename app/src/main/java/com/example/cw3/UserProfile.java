package com.example.cw3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.example.cw3.databinding.ActivityUserProfileBinding;
import com.example.cw3.viewmodels.AddUserVM;

import java.util.Objects;

public class UserProfile extends AppCompatActivity {
//    private UserProfileVM model;
    ActivityUserProfileBinding activityUserProfileBinding;
    EditText a;
    EditText b;
    EditText c;
    AddUserVM model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(AddUserVM.class);
        activityUserProfileBinding = ActivityUserProfileBinding.inflate(LayoutInflater.from(this));
        setContentView(activityUserProfileBinding.getRoot());
        activityUserProfileBinding.setUsermodel(model);
        activityUserProfileBinding.setLifecycleOwner(this);
        Objects.requireNonNull(getSupportActionBar()).hide();

        activityUserProfileBinding.SaveButton.setOnClickListener(v ->{
        a = findViewById(R.id.UserName);
        b= findViewById(R.id.UserAge);
        c= findViewById(R.id.UserWeight);
        model.AddUserProfileToDB(a.getText().toString(),Integer.parseInt(b.getText().toString()),Double.parseDouble(c.getText().toString()));
        finish();
        });
    }
}