package com.app.gpsphonelocator_new;

import static com.app.gpsphonelocator_new.database.UserDatabase.instance;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.CalendarContract;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {UserFriend.class}, version = 1)
public abstract class UserFriendDatabase  extends RoomDatabase {

    public static UserFriendDatabase instance;
    public abstract UserFriendDao Dao();

    public static  UserFriendDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context,UserFriendDatabase.class,"UserFriends").fallbackToDestructiveMigration().addCallback(roomCallback).build();

        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback= new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(UserFriendDatabase instance) {
            UserFriendDao dao = instance.Dao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
