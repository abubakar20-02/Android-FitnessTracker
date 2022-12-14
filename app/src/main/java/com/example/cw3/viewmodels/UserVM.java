package com.example.cw3.viewmodels;


import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cw3.Database.UserProfile;
import com.example.cw3.entities.UserProfileEntities;

import java.util.ArrayList;
import java.util.List;

public class UserVM extends ObservableVM{

    private final MutableLiveData<List<UserProfileEntities>> userProfile;
    private final LiveData<List<UserProfileEntities>> ProfileList;
    private List<UserProfileEntities> UserList;
    private LiveData<String> SelectedUser;
    private int listPosition;


    public UserVM(Application application) {
        super(application);
        UserProfile repository = new UserProfile(application);

        UserList = new ArrayList<>();

        userProfile = new MutableLiveData<>();
        userProfile.setValue(new ArrayList<>());
        ProfileList = repository.getAllUsers();
        SelectedUser = repository.getSelectedUser();

    }

    public void setByRating(List<UserProfileEntities> users) {
        UserList =users;
        getAllUsers().setValue(UserList);
        //        getAllUsers().setValue(UserProfiles);
    }

    public LiveData<List<UserProfileEntities>> GetAllUserProfiles() { return ProfileList; }


    public MutableLiveData<List<UserProfileEntities>> getAllUsers() { return userProfile; }

    public LiveData<String> GetSelectedUser() { return SelectedUser; }

    //Get position in list where user selected
    public int getListPosition() {
        return listPosition;
    }
    //Set position in list where user selected
    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }

}
