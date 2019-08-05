package io.tstud.paperweight.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.tstud.paperweight.Model.Models.Collection;
import io.tstud.paperweight.Model.Models.VolumeInfo;
import io.tstud.paperweight.Model.Repository;


public class HomeViewModel extends ViewModel {

    private LiveData<List<String>> books;
    private LiveData<VolumeInfo> volumeInfo = null;
    private LiveData<Collection> collection = null;

    private Repository repository;

    public HomeViewModel() {

        repository = Repository.getInstance();
        collection = repository.getTrendingList();
    }

    public LiveData<VolumeInfo> makeSingleBookRequest() {
        return volumeInfo;
    }

    public LiveData<Collection> getTrendingBooks() {
        return collection;
    }

    public void updateData() {
        collection = repository.getTrendingList();
    }

}
