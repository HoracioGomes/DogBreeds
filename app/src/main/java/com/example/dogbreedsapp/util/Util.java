package com.example.dogbreedsapp.util;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dogbreedsapp.R;

public class Util {
    public static void loadImage(ImageView imageView, String url, CircularProgressDrawable spinner) {
        RequestOptions options = new RequestOptions()
                .placeholder(spinner)
                .error(R.mipmap.ic_app);

        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(options)
                .load(url)
                .into(imageView);
    }

    public static CircularProgressDrawable getProgressdrawable(Context context) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(10f);
        circularProgressDrawable.setCenterRadius(50f);

        circularProgressDrawable.start();
        return circularProgressDrawable;
    }

    @BindingAdapter("android:urlImage")
    public static void loadImageBinding(ImageView imageView, String url){
        loadImage(imageView, url, getProgressdrawable(imageView.getContext()));
    }
}
