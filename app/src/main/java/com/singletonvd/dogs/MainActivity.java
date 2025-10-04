package com.singletonvd.dogs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;

    private ImageView imageViewDog;
    private Button buttonNextImage;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setObservers(viewModel);

        buttonNextImage.setOnClickListener(view -> viewModel.loadDogImage());

        if (!viewModel.getDogImage().isInitialized()) {
            viewModel.loadDogImage();
        }
    }


    private void initViews() {
        imageViewDog = findViewById(R.id.imageViewDog);
        buttonNextImage = findViewById(R.id.buttonNextImage);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setObservers(MainViewModel viewModel) {
        viewModel.getIsLoading().observe(this, isLoading -> {
            int visibility = isLoading ? View.VISIBLE : View.GONE;
            progressBar.setVisibility(visibility);
        });

        viewModel.getDogImage().observe(this, dogImage -> Glide.with(this)
                .load(dogImage.getImage())
                .into(imageViewDog));

        viewModel.getIsError().observe(this, isError -> {
            if (isError) {
                Toast.makeText(this, R.string.loading_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}