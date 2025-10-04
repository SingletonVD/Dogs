package com.singletonvd.dogs;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String BASE_URL = "https://dog.ceo/api/breeds/image/random";
    private static final String MESSAGE_FIELD_NAME = "message";
    private static final String STATUS_FIELD_NAME = "status";
    private static final String LOG_TAG = "MainViewModel";
    private final MutableLiveData<DogImage> dogImage = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadDogImage() {
        Disposable disposable = loadDogImageRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dogImage::setValue, e -> Log.d(LOG_TAG, e.toString()));
        compositeDisposable.add(disposable);
    }

    public LiveData<DogImage> getDogImage() {
        return dogImage;
    }

    private Single<DogImage> loadDogImageRx() {
        return Single.fromCallable(() -> {
            URL url = new URL(BASE_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String result;
            do {
                result = bufferedReader.readLine();
                if (result != null) {
                    stringBuilder.append(result);
                }
            } while (result != null);

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            String message = jsonObject.getString(MESSAGE_FIELD_NAME);
            String status = jsonObject.getString(STATUS_FIELD_NAME);
            return new DogImage(message, status);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
