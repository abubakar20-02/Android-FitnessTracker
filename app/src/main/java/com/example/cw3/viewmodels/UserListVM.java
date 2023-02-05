package com.example.cw3.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cw3.Database.UserDB;
import com.example.cw3.entities.users;

import java.util.ArrayList;
import java.util.List;

public class UserListVM extends ObservableVM{

    private final MutableLiveData<List<users>> userProfile;
    private final LiveData<List<users>> ProfileList;
    private List<users> UserList;
    private LiveData<String> SelectedUser;


    public UserListVM(Application application) {
        super(application);
        UserDB repository = new UserDB(application);

        UserList = new ArrayList<>();
        userProfile = new MutableLiveData<>();
        userProfile.setValue(new ArrayList<>());
        ProfileList = repository.getAllUsers();
        SelectedUser = repository.getSelectedUser();
    }

    public void setByUser(List<users> users) {
        UserList =users;
        getAllUsers().setValue(UserList);
    }

    public LiveData<List<users>> GetAllUserProfiles() { return ProfileList; }

    public MutableLiveData<List<users>> getAllUsers() { return userProfile; }

    public LiveData<String> GetSelectedUser() { return SelectedUser; }

}
