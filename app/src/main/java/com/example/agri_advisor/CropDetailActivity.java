package com.example.agri_advisor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class CropDetailActivity extends AppCompatActivity {

    private TextView cropNameTextView, cropDescriptionTextView;
    private ImageView cropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crop_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize views
        cropNameTextView = findViewById(R.id.cropNameTextView);
        cropDescriptionTextView = findViewById(R.id.cropDescriptionTextView);
        cropImageView = findViewById(R.id.cropImageView);

        // Get crop data from intent
        Intent intent = getIntent();
        String cropName = intent.getStringExtra("CROP_NAME");
        String cropDescription = intent.getStringExtra("CROP_DESCRIPTION");
        String cropImageUrl = intent.getStringExtra("CROP_IMAGE_URL");

        // Set data to UI elements
        cropNameTextView.setText(cropName);
        cropDescriptionTextView.setText(cropDescription);
        Glide.with(this).load(cropImageUrl).into(cropImageView);
    }
}