package io.tstud.paperweight.Home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.tstud.paperweight.Model.Models.Collection;
import io.tstud.paperweight.Model.Models.VolumeInfo;
import io.tstud.paperweight.Model.Repository;
import io.tstud.paperweight.Utils.NoNetworkInterceptor;
import io.tstud.paperweight.Utils.SingleLiveEvent;


public class HomeViewModel extends ViewModel {

    private LiveData<List<String>> books;
    private LiveData<VolumeInfo> volumeInfo = null;
    private MutableLiveData<Collection> collection = new MutableLiveData<>();
    private CompositeDisposable disposables = new CompositeDisposable();
    private SingleLiveEvent<String> error = new SingleLiveEvent<>();

    private Repository repository;

    public HomeViewModel() {
        repository = Repository.getInstance();
        setupTrendingList();
    }

    public void setupTrendingList() {
        repository.getTrendingList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> disposables.add(disposable))
                .doOnSuccess(items -> {
                    collection.postValue(items);
                    Log.d("repo", "fetched new content");
                })
                .doOnError(throwable -> {
                    if (throwable instanceof NoNetworkInterceptor.NoNetworkException)
                        error.postValue(throwable.getMessage());
                    else
                        Log.e("trending list", throwable.getMessage());
                })
                .subscribe();
    }

    public LiveData<VolumeInfo> makeSingleBookRequest() {
        return volumeInfo;
    }

    public LiveData<Collection> getTrendingBooks() {
        return collection;
    }

    public LiveData<String> getErrors() {
        return error;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
