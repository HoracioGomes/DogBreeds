package com.example.dogbreedsapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogbreedsapp.R;
import com.example.dogbreedsapp.databinding.CardDogBinding;
import com.example.dogbreedsapp.model.DogBreed;
import com.example.dogbreedsapp.util.Util;

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
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardDogBinding cardDogBinding = DataBindingUtil.inflate(layoutInflater, R.layout.card_dog, parent, false);
        return new DogViewHolder(cardDogBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
//        ImageView imageDog = holder.itemView.findViewById(R.id.iv_card_dog);
//        TextView name = holder.itemView.findViewById(R.id.tv_dog_name_card_dog);
//        TextView lifeSpan = holder.     itemView.findViewById(R.id.tv_dog_lifespan_card_dog);
//        LinearLayout llCardDog = holder.itemView.findViewById(R.id.linearlayout_card_dog);
//
//        name.setText(dogList.get(position).dogBreed);
//        lifeSpan.setText(dogList.get(position).lifeSpan);
//        Util.loadImage(imageDog, dogList.get(position).imageUrl, Util.getProgressdrawable(imageDog.getContext()));
//
//        llCardDog.setOnClickListener(view -> {
//            ListFragmentDirections.ActionListFragmentToDetailFragment action
//                    = ListFragmentDirections.actionListFragmentToDetailFragment(dogList.get(position).uuid);
//
//            Navigation.findNavController(llCardDog).navigate(action);
//        });

        holder.itemView.setDogBreed(dogList.get(position));

    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    class DogViewHolder extends RecyclerView.ViewHolder {
        public CardDogBinding itemView;

        public DogViewHolder(@NonNull CardDogBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
