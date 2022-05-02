package com.example.dogbreedsapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dogbreedsapp.model.DogBreed;
import com.example.dogbreedsapp.retrofit.DogsService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends AndroidViewModel {
    public MutableLiveData<List<DogBreed>> dogs = new MutableLiveData<List<DogBreed>>();
    public MutableLiveData<Boolean> dogLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();
    private DogsService dogsService = new DogsService();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        getFromRemote();
    }

    private void getFromRemote() {
        loading.setValue(true);
        compositeDisposable.add(
                dogsService.getDogBreeds()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                            @Override
                            public void onSuccess(@NonNull List<DogBreed> dogBreeds) {
                                dogs.setValue(dogBreeds);
                                dogLoadError.setValue(false);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                dogLoadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();
                            }

                        })

        );

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
