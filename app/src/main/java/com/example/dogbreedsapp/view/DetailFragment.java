package com.example.dogbreedsapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogbreedsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailFragment extends Fragment {

    public DetailFragment() {
    }

    @BindView(R.id.fab_details)
    FloatingActionButton fabDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fabDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToList();
            }
        });
    }

    private void goToList() {
        NavDirections action = DetailFragmentDirections.actionDetailFragmentToListFragment();
        Navigation.findNavController(fabDetails).navigate(action);
    }
}