package com.app.gpsphonelocator_new;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserFriendDao {
    @Insert
    void insert(UserFriend model);

    @Delete
    void delete(UserFriend model);

    @Query("DELETE FROM userfriend_table")
    void deleteAllFriends();

    @Query("SELECT * FROM userfriend_table ORDER BY name ASC")
    LiveData<List<UserFriend>> getAllUser();
}