package com.example.cw3.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cw3.Adapter.UserRecycleAdapter;
import com.example.cw3.R;
import com.example.cw3.databinding.ActivityUsersBinding;
import com.example.cw3.viewmodels.UserListVM;

import java.util.Objects;

public class usersList extends AppCompatActivity {

    ActivityUsersBinding activityUsersBinding;
    private UserRecycleAdapter UserRecycleAdapter;
    UserListVM userListVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        userListVM = new ViewModelProvider(this).get(UserListVM.class);
        activityUsersBinding = ActivityUsersBinding.inflate(LayoutInflater.from(this));
        setContentView(activityUsersBinding.getRoot());
        activityUsersBinding.setViewmodel(userListVM);
        activityUsersBinding.setLifecycleOwner(this);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //Adapter for showing all the users
        UserRecycleAdapter = new UserRecycleAdapter(this);

        activityUsersBinding.AddUser.setOnClickListener(v -> {
            Intent intent = new Intent(usersList.this, UserProfile.class);
            startActivity(intent);
        });
        activityUsersBinding.RecyclerView.setAdapter(UserRecycleAdapter);
        activityUsersBinding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityUsersBinding.RecyclerView.setItemAnimator(new DefaultItemAnimator());

        userListVM.GetAllUserProfiles().observe(this, users -> userListVM.setByUser(users));

        userListVM.getAllUsers().observe(this, userProfiles -> UserRecycleAdapter.setData(userProfiles));

        userListVM.GetSelectedUser().observe(this, a-> userListVM.GetSelectedUser());

        //When a recycler view item is selected ask for storage permissions to view the course
        UserRecycleAdapter.setClickListener((view, position) -> {
            Intent intent = new Intent(usersList.this, UserSelected.class);

            intent.putExtra("userName", UserRecycleAdapter.getUserName(position));
            intent.putExtra("userAge", UserRecycleAdapter.getUserAge(position));
            intent.putExtra("userWeight", UserRecycleAdapter.getUserWeight(position));
            intent.putExtra("userSelected", UserRecycleAdapter.getUserSelected(position));
            startActivity(intent);
        });

    }

    @Override
    protected void onDestroy() {
        userListVM.getAllUsers().removeObservers(this);
        super.onDestroy();
    }

}