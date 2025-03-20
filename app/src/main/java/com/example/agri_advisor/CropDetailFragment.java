package com.example.agri_advisor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class CropDetailFragment extends Fragment {
    private CropModel crop;

    public static CropDetailFragment newInstance(CropModel crop) {
        CropDetailFragment fragment = new CropDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("crop", crop);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crop_detail, container, false);

        if (getArguments() != null) {
            crop = (CropModel) getArguments().getParcelable("crop");

            TextView cropName = view.findViewById(R.id.cropName);
            ImageView cropImage = view.findViewById(R.id.cropImage);
            TextView cropDescription = view.findViewById(R.id.cropDescription);

            cropName.setText(crop.getCropName());
            cropDescription.setText(crop.getDescription());
            Glide.with(this).load(crop.getImageUrl()).placeholder(R.drawable.placeholder_image).error(R.drawable.error_image).into(cropImage);
        }

        return view;
    }
}
