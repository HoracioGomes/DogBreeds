package com.example.dogbreedsapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dogbreedsapp.model.DogBreed;

import java.util.List;

@Dao
public interface DogBreedDao {
    @Insert
    List<Long> insertAll(DogBreed... dogBreeds);

    @Query("SELECT * FROM dogbreed")
    List<DogBreed> getAll();

    @Query("SELECT * FROM dogbreed WHERE uuid = :id")
    DogBreed getFromId(int id);

    @Query("DELETE FROM dogbreed")
    void deleteAll();
}
