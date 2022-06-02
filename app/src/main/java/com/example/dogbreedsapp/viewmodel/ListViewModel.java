package com.example.dogbreedsapp.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dogbreedsapp.asynctask.InsertDogsAsyncTask;
import com.example.dogbreedsapp.asynctask.RetrieveStorageDogBreedAsyncTask;
import com.example.dogbreedsapp.asynctask.listeners.ListenerPosStorageDogBreeds;
import com.example.dogbreedsapp.asynctask.listeners.ListenerStorageRetrievedDogBreed;
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
    private AsyncTask<List<DogBreed>, Void, List<DogBreed>> insertDogsAsyncTask;
    private AsyncTask<Void, Void, List<DogBreed>> retrieveDogListAsyncTask;

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        retrieveDbDogList();
    }

    public void getFromRemote() {
        loading.setValue(true);
        compositeDisposable.add(
                dogsService.getDogBreeds()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                            @Override
                            public void onSuccess(@NonNull List<DogBreed> dogBreeds) {
                                afterGetApi(dogBreeds);
                                retrieveDbDogList();
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

    private void afterGetApi(@NonNull List<DogBreed> dogBreeds) {
        insertDogsAsyncTask = new InsertDogsAsyncTask(getApplication(), () -> retrieveDbDogList()).execute(dogBreeds);

    }

    public void retrieveDbDogList() {
        retrieveDogListAsyncTask = new RetrieveStorageDogBreedAsyncTask(getApplication(), retrievedList -> {
            dogs.setValue(retrievedList);
            dogLoadError.setValue(false);
            loading.setValue(false);
        }).execute();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
        if (insertDogsAsyncTask != null) {
            insertDogsAsyncTask.cancel(true);
            insertDogsAsyncTask = null;
        }

        if (retrieveDogListAsyncTask != null) {
            retrieveDogListAsyncTask.cancel(true);
            retrieveDogListAsyncTask = null;
        }
    }
}
