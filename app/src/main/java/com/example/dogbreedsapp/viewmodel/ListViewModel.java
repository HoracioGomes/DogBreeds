package com.example.dogbreedsapp.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dogbreedsapp.asynctask.InsertDogsAsyncTask;
import com.example.dogbreedsapp.asynctask.RetrieveStorageDogBreedAsyncTask;
import com.example.dogbreedsapp.model.DogBreed;
import com.example.dogbreedsapp.retrofit.DogsService;
import com.example.dogbreedsapp.util.NotificationHelper;
import com.example.dogbreedsapp.util.SharedPreferencesHelper;

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
    private SharedPreferencesHelper preferencesHelper = SharedPreferencesHelper.getInstance(getApplication());
    private long refreshTime = createRefreshTime();

    private long createRefreshTime() {
        return 5 * 60 * 1000 * 1000 * 1000L;
    }

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public void mainRefresh() {
        checkPrefsRefreshTime();
        long updatedTime = preferencesHelper.getUpdatedTime();
        long currentTime = System.nanoTime();
        if (updatedTime != 0 && currentTime - updatedTime < refreshTime) {
            getFromStorage();
        } else {
            getFromRemote();
        }
    }

    private void checkPrefsRefreshTime() {
        String unformattedRefreshTime = preferencesHelper.getPrefTime();
        if (!unformattedRefreshTime.equals("")) {
            try {
                Long formattedRefreshTime = Long.parseLong(unformattedRefreshTime);
                refreshTime = formattedRefreshTime * 1000 * 1000 * 1000L;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public void remoteRefresh() {
        getFromRemote();
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
                                NotificationHelper.getInstance(getApplication()).createNotification();
                                afterGetApi(dogBreeds);
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
        insertDogsAsyncTask = new InsertDogsAsyncTask(getApplication(), () -> {
            preferencesHelper.saveUpdateTime(System.nanoTime());
            getFromStorage();
        }).execute(dogBreeds);

    }

    public void getFromStorage() {
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
