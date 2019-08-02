package io.tstud.paperweight.BookDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import io.tstud.paperweight.Model.DaoModels.BookDaoModel;
import io.tstud.paperweight.Model.VolumeInfo;
import io.tstud.paperweight.Repo.Repository;

/**
 * Created by etino7 on 30/06/2019.
 */
public class DetailViewModel extends ViewModel {

    private LiveData<VolumeInfo> bookInfo;

    private Repository repo;

    public DetailViewModel(){
        repo = Repository.getInstance();

        // TODO: just a test, doesn't provide id for the book
        bookInfo = repo.getSingleTest();
    }

    public LiveData<VolumeInfo> getBookInfo(String bookId){

        return bookInfo;
    }

    public void saveBook(BookDaoModel bookToSave){
        repo.saveBookToLocal(bookToSave);
    }
}
