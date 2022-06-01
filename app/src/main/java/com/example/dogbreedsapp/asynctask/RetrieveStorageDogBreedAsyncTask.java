package com.example.dogbreedsapp.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.dogbreedsapp.asynctask.listeners.ListenerStorageRetrievedDogBreed;
import com.example.dogbreedsapp.database.AppDatabase;
import com.example.dogbreedsapp.database.DogBreedDao;
import com.example.dogbreedsapp.model.DogBreed;

import java.util.List;

public class RetrieveStorageDogBreedAsyncTask extends AsyncTask<Void, Void, List<DogBreed>> {
    private ListenerStorageRetrievedDogBreed listener;
    private Context context;
    private List<DogBreed> dogBreedList;

    public RetrieveStorageDogBreedAsyncTask( Context context, ListenerStorageRetrievedDogBreed listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected List<DogBreed> doInBackground(Void... voids) {
        DogBreedDao dogBreedDao = AppDatabase.getAppDatabase(context).dogBreedDao();
        dogBreedList = dogBreedDao.getAll();
        return dogBreedList;
    }

    @Override
    protected void onPostExecute(List<DogBreed> dogBreeds) {
        super.onPostExecute(dogBreeds);
        listener.retrievedList(dogBreedList);
    }
}
