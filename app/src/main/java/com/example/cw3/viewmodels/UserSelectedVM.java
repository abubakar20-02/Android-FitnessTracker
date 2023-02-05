package com.example.cw3.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import com.example.cw3.Database.UserDB;

public class UserSelectedVM extends ObservableVM{

    // wrong approach (this was the first thing I made.)
    @Bindable
    public String userName;
    @Bindable
    public String userAge;
    @Bindable
    public String userWeight;
    @Bindable
    public String userSelected;

    private final UserDB userRep;

    public UserSelectedVM(@NonNull Application application) {
        super(application);
        userRep = new UserDB(application);
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
    public void addUserSelected(boolean selected) {userSelected= String.valueOf(selected);}
    public void DeleteProfile(){ userRep.deleteUserProfile(userName);}
    public void RemoveAllSelectedProfile() { userRep.setAllUserSelectedTo(false); }
    public void selectProfile() { userRep.selectProfile(userName); }
}
