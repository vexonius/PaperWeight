package io.tstud.paperweight.Home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.tstud.paperweight.Model.Collection;
import io.tstud.paperweight.Model.VolumeInfo;
import io.tstud.paperweight.Repo.Repository;


public class HomeViewModel extends ViewModel {

    private LiveData<List<String>> books;
    private LiveData<VolumeInfo> volumeInfo = null;
    private LiveData<Collection> collection = null;

    private Repository repository;

    public HomeViewModel() {
        super();
        repository = Repository.getInstance();
        collection = repository.getTrendingList();
    }

    public LiveData<VolumeInfo> makeSingleBookRequest() {
        return volumeInfo;
    }

    public LiveData<Collection> getTrendingBooks() {
        return collection;
    }

    public void updateData(){
        collection = repository.getTrendingList();
    }

}
