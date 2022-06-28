package com.example.dogbreedsapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dogbreedsapp.asynctask.SearchDogBreedFromUuidAsyncTask;
import com.example.dogbreedsapp.asynctask.listeners.ListenerPosSearchDogBreed;
import com.example.dogbreedsapp.model.DogBreed;

public class DetailViewModel extends AndroidViewModel {
    public MutableLiveData<DogBreed> dogBreedDetail = new MutableLiveData<DogBreed>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> error = new MutableLiveData<Boolean>();

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }


    public void fetch(int uuid) {

        new SearchDogBreedFromUuidAsyncTask(getApplication(), uuid, new ListenerPosSearchDogBreed() {
            @Override
            public void posSearch(DogBreed dogBreed) {

                if (dogBreed != null) {
                    dogBreedDetail.setValue(dogBreed);
                    error.setValue(false);
                    loading.setValue(false);
                }
            }
        }).execute();


    }
}
