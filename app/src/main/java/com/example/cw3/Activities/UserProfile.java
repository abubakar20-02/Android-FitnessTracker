package com.example.cw3.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cw3.R;
import com.example.cw3.databinding.ActivityUserProfileBinding;
import com.example.cw3.viewmodels.AddUserVM;

import java.util.Objects;

public class UserProfile extends AppCompatActivity {
    ActivityUserProfileBinding activityUserProfileBinding;
    EditText userName;
    EditText userAge;
    EditText userWeight;
    AddUserVM addUserVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addUserVM = new ViewModelProvider(this).get(AddUserVM.class);
        activityUserProfileBinding = ActivityUserProfileBinding.inflate(LayoutInflater.from(this));
        setContentView(activityUserProfileBinding.getRoot());
        activityUserProfileBinding.setUsermodel(addUserVM);
        activityUserProfileBinding.setLifecycleOwner(this);
        Objects.requireNonNull(getSupportActionBar()).hide();

        activityUserProfileBinding.SaveButton.setOnClickListener(v ->{
        userName = findViewById(R.id.UserName);
        userAge = findViewById(R.id.UserAge);
        userWeight = findViewById(R.id.UserWeight);

        // if all data is not complete, don't add to database and let the user know.
        if (userName.getText().toString().equals("") || userAge.getText().toString().equals("") || userWeight.getText().toString().equals("")){
            Toast.makeText(this,"data cannot be empty",Toast.LENGTH_SHORT).show();
        }
        else {
            addUserVM.AddUserProfileToDB(userName.getText().toString(), Integer.parseInt(userAge.getText().toString()), Double.parseDouble(userWeight.getText().toString()));
            finish();
        }
        });
    }
}