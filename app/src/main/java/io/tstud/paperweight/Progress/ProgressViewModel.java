package io.tstud.paperweight.Progress;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.tstud.paperweight.Model.Models.BookWithStats;
import io.tstud.paperweight.Model.Repository;

/**
 * Created by etino7 on 11/08/2019.
 */
public class ProgressViewModel extends ViewModel {

    private LiveData<List<BookWithStats>> booksWithStats = new MutableLiveData<>();
    private MutableLiveData<BookWithStats> bookToUpdate = new MutableLiveData<>();
    private Repository repo;

    public ProgressViewModel(){
        repo = Repository.getInstance();
        booksWithStats = repo.getAllBooksWithStats();
    }

    public LiveData<List<BookWithStats>> getData(){
        return booksWithStats;
    }

    public void updateBookProgress(int progress){
        repo.updateBookProgress(bookToUpdate.getValue().getStats(), progress);
    }

    public void setBookToUpdate(BookWithStats bookToUpdate){
        this.bookToUpdate.setValue(bookToUpdate);
    }

    public LiveData<BookWithStats> getBookToUpdate(){
        return bookToUpdate;
    }

}
