package com.example.dogbreedsapp.retrofit;

import com.example.dogbreedsapp.model.DogBreed;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogsService {
    private static final String BASE_URL = "https://raw.githubusercontent.com";
    private DogsApi dogsApi;

    public DogsService() {
        dogsApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(DogsApi.class);
    }

    public Single<List<DogBreed>> getDogBreeds() {
        return dogsApi.getDogs();
    }
}
