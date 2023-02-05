package com.example.cw3.viewmodels;

import android.app.Application;
import android.util.Log;

import com.example.cw3.Database.UserDB;
import com.example.cw3.entities.users;

public class AddUserVM extends ObservableVM{
    UserDB userRep;
    users userProfileEntities;

    public AddUserVM(Application application) {
        super(application);

        userRep = new UserDB(application);
        userProfileEntities= new users();
    }

        public void AddUserProfileToDB(String UserName, int UserAge, double UserWeight){
        userProfileEntities.setUserName(UserName);
        userProfileEntities.setUserAge(UserAge);
        userProfileEntities.setUserWeight(UserWeight);
        userProfileEntities.setUserSelected(false);

        Log.d("UserName", userProfileEntities.getUserName());
        Log.d("UserAge", Integer.toString(userProfileEntities.getUserAge()));
        Log.d("UserWeight", Double.toString(userProfileEntities.getUserWeight()));

        userRep.insertUserProfile(userProfileEntities);
        }
}
