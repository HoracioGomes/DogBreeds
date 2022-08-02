package com.example.dogbreedsapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dogbreedsapp.R;
import com.example.dogbreedsapp.databinding.FragmentDetailBinding;
import com.example.dogbreedsapp.model.DogBreed;
import com.example.dogbreedsapp.util.Util;
import com.example.dogbreedsapp.viewmodel.DetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailFragment extends Fragment {
    private int dogUuid;

//    @BindView(R.id.tv_dog_name_details)
//    TextView dogName;
//    @BindView(R.id.tv_dog_purpose_details)
//    TextView dogPurpose;
//    @BindView(R.id.tv_dog_temperament_details)
//    TextView dogTemperament;
//    @BindView(R.id.tv_dog_lifespan_details)
//    TextView dogLifespan;
//    @BindView(R.id.iv_dog_details)
//    ImageView dogCircularImage;

    public DetailFragment() {
    }

    private DetailViewModel detailViewModel;
    private FragmentDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_detail, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        initObserver();
        if (getArguments() != null) {
            dogUuid = DetailFragmentArgs.fromBundle(getArguments()).getDogUuid();
            detailViewModel.fetch(dogUuid);
        }

    }

    private void initObserver() {
        detailViewModel.dogBreedDetail.observe(getViewLifecycleOwner(), new Observer<DogBreed>() {
            @Override
            public void onChanged(DogBreed dogBreed) {
//                dogName.setText(dogBreed.dogBreed);
//                dogTemperament.setText(dogBreed.temperament);
//                dogLifespan.setText(dogBreed.lifeSpan);
//                dogPurpose.setText(dogBreed.bredFor);
//                Util.loadImage(dogCircularImage,dogBreed.imageUrl,null);

                binding.setDog(dogBreed);

            }
        });
    }

}