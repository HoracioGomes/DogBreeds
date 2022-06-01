package com.example.dogbreedsapp.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.dogbreedsapp.asynctask.listeners.ListenerPosStorageDogBreeds;
import com.example.dogbreedsapp.database.AppDatabase;
import com.example.dogbreedsapp.database.DogBreedDao;
import com.example.dogbreedsapp.model.DogBreed;

import java.util.ArrayList;
import java.util.List;

public class InsertDogsAsyncTask extends AsyncTask<List<DogBreed>, Void, List<DogBreed>> {
    private Context context;
    private ListenerPosStorageDogBreeds listener;

    public InsertDogsAsyncTask(Context context, ListenerPosStorageDogBreeds listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<DogBreed> doInBackground(List<DogBreed>... lists) {
        List<DogBreed> list = lists[0];
        DogBreedDao dao = AppDatabase.getAppDatabase(context).dogBreedDao();
        dao.deleteAll();
        ArrayList<DogBreed> newList = new ArrayList<>(list);
        List<Long> result = dao.insertAll(newList.toArray(new DogBreed[0]));

        for (int i = 0; i < list.size(); i++) {
            list.get(i).uuid = result.get(i).intValue();
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<DogBreed> dogBreeds) {
        super.onPostExecute(dogBreeds);
        listener.listStoraged();
    }
}

