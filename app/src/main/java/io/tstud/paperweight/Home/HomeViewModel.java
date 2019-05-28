package io.tstud.paperweight.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.tstud.paperweight.Model.VolumeInfo;
import io.tstud.paperweight.Repo.Repository;


public class HomeViewModel extends ViewModel {

    private LiveData<List<String>> books;
    private LiveData<VolumeInfo> volumeInfo = null;

    private Repository repository;

    public HomeViewModel(){
        super();
        repository = new Repository();
    }

    public LiveData<VolumeInfo> makeSingleBookRequest(){
        if(volumeInfo == null)
            return repository.getVolumeById("rIj5x-C7D2cC");
        else
            return volumeInfo;
    }

    public void getTrendingBooks(){

    }

}
