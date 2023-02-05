package com.example.cw3.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

public class ObservableVM extends AndroidViewModel implements Observable {

    PropertyChangeRegistry callbacks = new PropertyChangeRegistry();

    public ObservableVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }

    public void notifyPropertyChanged(int fieldId) {
        callbacks.notifyCallbacks(this, fieldId, null);
    }
}
