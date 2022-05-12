package com.example.dogbreedsapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dogbreedsapp.model.DogBreed;

@Database(entities = {DogBreed.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase database;

    public static AppDatabase getAppDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    "dogbreeddb")
                    .build();
        }
        return database;
    }

    public abstract DogBreedDao dogBreedDao();

}
