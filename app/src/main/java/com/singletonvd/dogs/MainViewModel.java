package com.singletonvd.dogs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {
    private final ApiService apiService = ApiFactory.getApiService();

    private final MutableLiveData<DogImage> dogImage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isError = new MutableLiveData<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadDogImage() {
        Disposable disposable = loadDogImageRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> {
                    isLoading.setValue(true);
                    isError.setValue(false);
                })
                .doAfterTerminate(() -> isLoading.setValue(false))
                .subscribe(dogImage::setValue, e -> isError.setValue(true));
        compositeDisposable.add(disposable);
    }

    public LiveData<DogImage> getDogImage() {
        return dogImage;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getIsError() {
        return isError;
    }

    private Single<DogImage> loadDogImageRx() {
        return apiService.loadDogImage();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
