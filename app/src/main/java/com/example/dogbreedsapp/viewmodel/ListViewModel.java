package com.example.dogbreedsapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dogbreedsapp.model.DogBreed;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends AndroidViewModel {
    public MutableLiveData<List<DogBreed>> dogs = new MutableLiveData<List<DogBreed>>();
    public MutableLiveData<Boolean> dogLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        DogBreed dog1 = new DogBreed("1", "Pincher", "12", "", "", "", "");
        DogBreed dog2 = new DogBreed("1", "Pitbull", "12", "", "", "", "");
        DogBreed dog3 = new DogBreed("1", "Fila", "10", "", "", "", "");

        ArrayList<DogBreed> dogList = new ArrayList<DogBreed>();
        dogList.add(dog1);
        dogList.add(dog2);
        dogList.add(dog3);

        dogs.setValue(dogList);
        dogLoadError.setValue(false);
        loading.setValue(false);
    }
}