package com.example.dogbreedsapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dogbreedsapp.model.DogBreed;

public class DetailViewModel extends ViewModel {
    public MutableLiveData<DogBreed> dogBreedDetail = new MutableLiveData<DogBreed>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> error = new MutableLiveData<Boolean>();

    public void refresh() {
        DogBreed breed = new DogBreed("1", "nameBreed", "12", "group",
                "breedFor", "calm", "urlImage");
        dogBreedDetail.setValue(breed);

        error.setValue(false);
        loading.setValue(false);

    }
}
