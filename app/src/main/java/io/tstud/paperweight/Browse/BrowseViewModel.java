package io.tstud.paperweight.Browse;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.tstud.paperweight.Model.Models.Item;
import io.tstud.paperweight.Model.Repository;

/**
 * Created by etino7 on 02/06/2019.
 */
public class BrowseViewModel extends ViewModel {

    private LiveData<List<Item>> searchResults;
    private MutableLiveData<String> query = new MutableLiveData<>();
    private Repository repository;

    public BrowseViewModel() {
        repository = Repository.getInstance();
        setSearchQuery("");
        setupSearchLD();
    }

    private void setupSearchLD(){
        searchResults = Transformations.switchMap(query, search -> {

            if (search == null || search.trim().length() == 0) {
                // return recent searches as placeholder
                Log.d("browse viewmodel", "recent serach action triggered");
                return repository.getLastFiveRecentSearches();
            } else {
                Log.d("browse viewmodel", "received search items");
                return repository.getSearchVolumes(search);
            }
        });
    }

    public void setSearchQuery(@NonNull String query) {
        this.query.postValue(query);
    }

    public LiveData<List<Item>> getSearchResults() {
        return searchResults;
    }

}
