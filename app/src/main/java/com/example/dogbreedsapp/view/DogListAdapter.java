package com.example.dogbreedsapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogbreedsapp.R;
import com.example.dogbreedsapp.model.DogBreed;

import java.util.ArrayList;
import java.util.List;

public class DogListAdapter extends RecyclerView.Adapter<DogListAdapter.DogViewHolder> {
    private ArrayList<DogBreed> dogList;

    public DogListAdapter(ArrayList<DogBreed> dogList) {
        this.dogList = dogList;
    }

    public void update(List<DogBreed> updatedListDogBreed) {
        dogList.clear();
        dogList.addAll(updatedListDogBreed);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_dog, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        ImageView imageDog = holder.itemView.findViewById(R.id.iv_card_dog);
        TextView name = holder.itemView.findViewById(R.id.tv_dog_name_card_dog);
        TextView lifeSpan = holder.itemView.findViewById(R.id.tv_dog_lifespan_card_dog);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class DogViewHolder extends RecyclerView.ViewHolder {
        public View itemView;

        public DogViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
