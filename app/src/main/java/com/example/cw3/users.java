package com.example.cw3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cw3.Adapter.RecycleAdapter;
import com.example.cw3.databinding.ActivityUsersBinding;
import com.example.cw3.viewmodels.UserProfileVM;

import java.util.Objects;

public class users extends AppCompatActivity {

    ActivityUsersBinding activityUsersBinding;
    com.example.cw3.Adapter.RecycleAdapter RecycleAdapter;
    UserProfileVM model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        model = new ViewModelProvider(this).get(UserProfileVM.class);
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
//        model.getAllUsers().observe(this, a -> RecycleAdapter.setData(a));

        activityUsersBinding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityUsersBinding.RecyclerView.setItemAnimator(new DefaultItemAnimator());
        activityUsersBinding.RecyclerView.setAdapter(RecycleAdapter);

    }
}