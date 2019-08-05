package io.tstud.paperweight.BookDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import io.tstud.paperweight.Model.Models.Item;
import io.tstud.paperweight.Model.Repository;


public class DetailViewModel extends ViewModel {

    private LiveData<Item> book;

    private Repository repo;

    public DetailViewModel() {
        repo = Repository.getInstance();

        book = repo.getBookDetails();
    }

    public LiveData<Item> getBookInfo(String bookId) {

        return book;
    }

    public void saveCurrentBook() {
        repo.saveBookToLocal(book.getValue());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repo.disposeObservers();
    }
}
