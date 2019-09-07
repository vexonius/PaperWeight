package io.tstud.paperweight.BookDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.tstud.paperweight.Model.Models.Item;
import io.tstud.paperweight.Model.Repository;


public class DetailViewModel extends ViewModel {

    private MutableLiveData<Item> book = new MutableLiveData<>();

    private Repository repo;

    public DetailViewModel() {
        repo = Repository.getInstance();
    }

    public LiveData<Item> getBookInfo() {
            return book;
    }

    public void setBookId(String id){
        book = repo.getBookDetails(id);
    }

    public void saveCurrentBook() {
        repo.saveBookToLocal(book.getValue());
    }

    public void setCurrentlyReadingBook(String bookId){
        repo.saveBookToCurrentlyReading(bookId);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repo.disposeObservers();
    }
}
