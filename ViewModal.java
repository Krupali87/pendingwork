package com.app.gpsphonelocator_new;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModal extends AndroidViewModel {

    private  UserFriendRepository repository;
    private LiveData<List<UserFriend>> alluser;

    public ViewModal(@NonNull Application application) {
        super(application);
        repository = new UserFriendRepository(application);
        alluser = repository.getAllUser();
    }

    public void insert(UserFriend model) {
        repository.insert(model);
    }
    public void delete(UserFriend model) {
        repository.delete(model);
    }

    public LiveData<List<UserFriend>> alluser() {
        return alluser;
    }
}
