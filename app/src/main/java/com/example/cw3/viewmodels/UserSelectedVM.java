package com.example.cw3.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cw3.Database.UserProfile;

public class UserSelectedVM extends ObservableVM{

    @Bindable
    public String userName;
    @Bindable
    public String userAge;
    @Bindable
    public String userWeight;

    private final UserProfile repository;

    public UserSelectedVM(@NonNull Application application) {
        super(application);
        repository= new UserProfile(application);
    }
    public void addUserName(String UserName){
        userName=UserName;
    }
    public void addUserAge(int Age){
        userAge=Integer.toString(Age);
    }
    public void addUserWeight(double Weight){
        userWeight=Double.toString(Weight);
    }
    public void DeleteProfile(){
        repository.deleteUserProfile(userName);
    }
}
