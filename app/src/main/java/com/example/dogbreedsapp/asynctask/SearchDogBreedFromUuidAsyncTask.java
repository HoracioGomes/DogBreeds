package com.example.dogbreedsapp.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.dogbreedsapp.asynctask.listeners.ListenerPosSearchDogBreed;
import com.example.dogbreedsapp.database.AppDatabase;
import com.example.dogbreedsapp.database.DogBreedDao;
import com.example.dogbreedsapp.model.DogBreed;

public class SearchDogBreedFromUuidAsyncTask extends AsyncTask<Integer, Void, DogBreed> {
    private ListenerPosSearchDogBreed listener;
    private Context context;
    private int uuid;

    public SearchDogBreedFromUuidAsyncTask(Context context, int uuid, ListenerPosSearchDogBreed listener) {
        this.listener = listener;
        this.context = context;
        this.uuid = uuid;
    }

    @Override
    protected DogBreed doInBackground(Integer... ints) {

        DogBreedDao dogBreedDao = AppDatabase.getAppDatabase(context).dogBreedDao();
        DogBreed dogBreed = dogBreedDao.getFromId(uuid);
        return dogBreed;
    }

    @Override
    protected void onPostExecute(DogBreed dogBreed) {
        super.onPostExecute(dogBreed);
        listener.posSearch(dogBreed);
    }
}
