package com.example.dogbreedsapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dogbreedsapp.R;
import com.example.dogbreedsapp.model.DogBreed;
import com.example.dogbreedsapp.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment {

    private ListViewModel listViewModel;
    private DogListAdapter adapter = new DogListAdapter(new ArrayList<>());

    @BindView(R.id.refresh_layout_list_dog)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view_list_dog)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar_list_dog)
    ProgressBar loading;
    @BindView(R.id.text_view_error_list_dog)
    TextView error;

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        observeViewModel();
        listViewModel.getFromRemote();
    }

    private void observeViewModel() {
        listViewModel.dogs.observe(getViewLifecycleOwner(), new Observer<List<DogBreed>>() {
            @Override
            public void onChanged(List<DogBreed> dogBreeds) {
                if (dogBreeds != null && dogBreeds instanceof List) {
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.update(dogBreeds);
                }
            }
        });

        listViewModel.dogLoadError.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError != null && isError instanceof Boolean) {
                    error.setVisibility(isError ? View.VISIBLE : View.GONE);

                }
            }
        });

        listViewModel.loading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null && isLoading instanceof Boolean) {
                    loading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                    if (isLoading) {
                        error.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}