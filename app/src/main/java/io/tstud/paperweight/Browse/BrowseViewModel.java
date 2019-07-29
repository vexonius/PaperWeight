package io.tstud.paperweight.Browse;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import io.tstud.paperweight.Model.Collection;
import io.tstud.paperweight.Repo.Repository;

/**
 * Created by etino7 on 02/06/2019.
 */
public class BrowseViewModel extends ViewModel {

    private LiveData<Collection> searchResults;
    private MutableLiveData<String> query = new MutableLiveData<>();
    private Repository repository;

    public BrowseViewModel() {
        repository = Repository.getInstance();
        setupSearchLD();
    }

    private void setupSearchLD(){
        searchResults = Transformations.switchMap(query, search -> {

            if (search == null || search.trim().length() == 0) {
                // TODO: create absent livedata
                return new MutableLiveData<>();
            } else {
                return repository.getSearchVolumes(search);
            }
        });
    }

    public void setSearchQuery(@NonNull String query) {
        this.query.postValue(query);
    }

    public LiveData<Collection> getSearchResults() {
        return searchResults;
    }

}
