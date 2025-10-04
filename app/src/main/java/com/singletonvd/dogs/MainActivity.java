package com.singletonvd.dogs;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private static final String LOG_TAG = "MainActivity";

    private ImageView imageViewDog;
    private Button buttonNextImage;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.loadDogImage();
        viewModel.getDogImage().observe(this, dogImage -> Glide.with(this)
                .load(dogImage.getImage())
                .into(imageViewDog));
    }

    private void initViews() {
        imageViewDog = findViewById(R.id.imageViewDog);
        buttonNextImage = findViewById(R.id.buttonNextImage);
        progressBar = findViewById(R.id.progressBar);
    }
}