package io.tstud.paperweight.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.tstud.paperweight.Repo.Repository;


public class HomeViewModel extends ViewModel {

    private LiveData<List<String>> books;

    private Repository repository;

    public HomeViewModel(){
        super();
        repository = new Repository();
    }

    public void makeSingleBookRequest(){
        repository.getVolumeById("rIj5x-C7D2cC");
    }

    public void getTrendingBooks(){

    }

}
