package com.singletonvd.dogs;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.loadDogImage();
        viewModel.getDogImage().observe(this, dogImage -> {
            Log.d(LOG_TAG, dogImage.toString());
        });
    }
}