package com.example.cw3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
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
    private RecycleAdapter RecycleAdapter;
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
        //Adapter for showing all courses
        RecycleAdapter  = new RecycleAdapter(this);

        activityUsersBinding.AddUser.setOnClickListener(v -> {
            Intent intent = new Intent(users.this, UserProfile.class);
            startActivity(intent);
        });
        activityUsersBinding.RecyclerView.setAdapter(RecycleAdapter);
        activityUsersBinding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityUsersBinding.RecyclerView.setItemAnimator(new DefaultItemAnimator());


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

//         model.SelectAll().observe(this ,a -> model.SelectAll());
//        model.getAllUsers().observe(this, a -> RecycleAdapter.setData(a));

//        model.getCoursesByDate().observe(this, courses -> model.setByDate(courses, activityMainBinding.sortStats.getSelectedItem().toString()));

        model.GetAllUserProfiles().observe(this, courses -> model.setByRating(courses));

        model.getAllUsers().observe(this, userProfiles -> RecycleAdapter.setData(userProfiles));



        //When a recycler view item is selected ask for storage permissions to view the course
        RecycleAdapter.setClickListener((view, position) -> {
            Log.d("position",Integer.toString(position));
            Intent intent = new Intent(users.this, UserSelected.class);
            Log.d("UserName Selected",RecycleAdapter.getUserName(position));
            Log.d("UserAge Selected",Integer.toString(RecycleAdapter.getUserAge(position)));
            Log.d("UserWeight Selected",Double.toString(RecycleAdapter.getUserWeight(position)))
;
            intent.putExtra("userName",RecycleAdapter.getUserName(position));
            intent.putExtra("userAge",RecycleAdapter.getUserAge(position));
            intent.putExtra("userWeight",RecycleAdapter.getUserWeight(position));
//            intent.putExtra("message_key", str);
            startActivity(intent);

            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                startViewCourse(position);
                Log.d("1","1");
            }
            else {
                model.setListPosition(position);
                Log.d("2","2");
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            }
        });

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

    @Override
    protected void onStart() {
        Log.d("Start", "Start");
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        model.getAllUsers().removeObservers(this);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
//        model.SelectAll().removeObservers(this);
        super.onPause();


    }
}