package com.example.cw3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cw3.Adapter.RecycleAdapter;
import com.example.cw3.databinding.ActivityUsersBinding;
import com.example.cw3.viewmodels.UserVM;

import java.util.Objects;

public class users extends AppCompatActivity {

    ActivityUsersBinding activityUsersBinding;
    com.example.cw3.Adapter.RecycleAdapter RecycleAdapter;
    UserVM model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        model = new ViewModelProvider(this).get(UserVM.class);
        activityUsersBinding = ActivityUsersBinding.inflate(LayoutInflater.from(this));
        setContentView(activityUsersBinding.getRoot());
        activityUsersBinding.setViewmodel(model);
        activityUsersBinding.setLifecycleOwner(this);
        Objects.requireNonNull(getSupportActionBar()).hide();

        activityUsersBinding.AddUser.setOnClickListener(v -> {
            Intent intent = new Intent(users.this, UserProfile.class);
            startActivity(intent);
        });


//        activityUsersBinding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                model.setAllCourses(activityUsersBinding.getSelectedItem().toString());
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//            }
//        });

        //Adapter for showing all courses
         RecycleAdapter  = new RecycleAdapter(this);
//         model.SelectAll().observe(this ,a -> model.SelectAll());
//        model.getAllUsers().observe(this, a -> RecycleAdapter.setData(a));

        model.SelectAll().observe(this, userProfiles -> RecycleAdapter.setData(userProfiles));

        activityUsersBinding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityUsersBinding.RecyclerView.setItemAnimator(new DefaultItemAnimator());
        activityUsersBinding.RecyclerView.setAdapter(RecycleAdapter);

    }

//    //When a recycler view item is selected ask for storage permissions to view the course
//        courseAdapter.setClickListener((view, position) -> {
//
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            startViewCourse(position);
//        }
//        else {
//            model.setListPosition(position);
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
//        }
//    });
}