package com.example.cw3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.cw3.databinding.ActivityUserProfileBinding;

import java.util.Objects;

public class UserProfile extends AppCompatActivity {
//    private UserProfileVM model;
    ActivityUserProfileBinding activityUserProfileBinding;
//    TextView a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        model = new ViewModelProvider(this).get(UserProfileVM.class);
        activityUserProfileBinding = ActivityUserProfileBinding.inflate(LayoutInflater.from(this));
        setContentView(activityUserProfileBinding.getRoot());
//        activityUserProfileBinding.setUsermodel(model);
//        activityUserProfileBinding.setLifecycleOwner(this);
        Objects.requireNonNull(getSupportActionBar()).hide();

//        a = (TextView) findViewById(R.id.UserName);
//        a.setText(model.getUserName().toString());

//        activityUserProfileBinding.SaveButton.setOnClickListener(v -> {
////            model.setUserName(a.getText().toString());
////            a= (EditText) findViewById(R.id.UserAge);
////            model.setUserAge(Integer.parseInt(a.getText().toString()));
////            a= (EditText) findViewById(R.id.UserWeight);
////            model.setUserWeight(Double.valueOf(a.getText().toString()));
////            model.getUserName().observe(this, new Observer<String>() {
////                @Override
////                public void onChanged(String s) {
////                    model.Save();
////                }
////            });
//        });

    }
}