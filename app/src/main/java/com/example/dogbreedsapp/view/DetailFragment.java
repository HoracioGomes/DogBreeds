package com.example.dogbreedsapp.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.dogbreedsapp.R;
import com.example.dogbreedsapp.databinding.DialogSmsBinding;
import com.example.dogbreedsapp.databinding.FragmentDetailBinding;
import com.example.dogbreedsapp.model.DogBreed;
import com.example.dogbreedsapp.model.DogPalette;
import com.example.dogbreedsapp.model.Sms;
import com.example.dogbreedsapp.viewmodel.DetailViewModel;


public class DetailFragment extends Fragment {
    private int dogUuid;
    private boolean isSendSms = false;
    private DogBreed currentDog;

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
        setHasOptionsMenu(true);
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
                currentDog = dogBreed;
                binding.setDog(dogBreed);

                if (dogBreed.imageUrl != null) {
                    setupBackgroundColor(dogBreed.imageUrl);
                }

            }
        });
    }

    private void setupBackgroundColor(String url) {
        Glide.with(this).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(@Nullable Palette palette) {
                        int colorPalette = palette.getLightMutedSwatch().getRgb();
                        DogPalette dogPalette = new DogPalette(colorPalette);
                        binding.setDogPalette(dogPalette);
                    }
                });
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Check");
                intent.putExtra(Intent.EXTRA_TEXT,currentDog.dogBreed + " Description: " + currentDog.bredFor);
                intent.putExtra(Intent.EXTRA_STREAM, currentDog.imageUrl);
                startActivity(Intent.createChooser(intent, "Share With"));

                break;

            case R.id.menu_sms:

                if (!isSendSms) {
                    isSendSms = true;
                    ((MainActivity) getActivity()).checkSmsPermission();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPermissionResult(Boolean permissionGranted) {
        if (isAdded() && isSendSms && permissionGranted) {
            Sms smsInfo = new Sms("", currentDog.dogBreed + " Description: " + currentDog.bredFor, currentDog.imageUrl);

            DialogSmsBinding dialogSmsBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_sms, null, false);

            new AlertDialog.Builder(getContext())
                    .setView(dialogSmsBinding.getRoot())
                    .setPositiveButton("Send SMS", (dialogInterface, i) -> {
                        if (!dialogSmsBinding.smsDestination.getText().toString().isEmpty()) {
                            smsInfo.setTo(dialogSmsBinding.smsDestination.getText().toString());
                            sendSms(smsInfo);
                        }
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    })
                    .show();
            isSendSms = false;
            dialogSmsBinding.setSms(smsInfo);


        }
    }

    private void sendSms(Sms smsInfo) {
    }
}