package com.example.cw3.viewmodels;

import android.app.Application;
import android.util.Log;

import com.example.cw3.Database.UserProfile;
import com.example.cw3.entities.UserProfileEntities;

public class AddUserVM extends ObservableVM{
    UserProfile repository;
    UserProfileEntities userProfileEntities;

    public AddUserVM(Application application) {
        super(application);

        repository = new UserProfile(application);
        userProfileEntities= new UserProfileEntities();
    }

        public void AddUserProfileToDB(String UserName, int UserAge, double UserWeight){
        userProfileEntities.setUserName(UserName);
        userProfileEntities.setUserAge(UserAge);
        userProfileEntities.setUserWeight(UserWeight);
        userProfileEntities.setUserSelected(false);

        Log.d("UserName", userProfileEntities.getUserName());
        Log.d("UserAge", Integer.toString(userProfileEntities.getUserAge()));
        Log.d("UserWeight", Double.toString(userProfileEntities.getUserWeight()));

        repository.insertUserProfile(userProfileEntities);
        }
}
