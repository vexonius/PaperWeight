package io.tstud.paperweight.Local;

import androidx.room.RoomDatabase;

import io.tstud.paperweight.Model.Dao.BookDao;
import io.tstud.paperweight.Model.DaoModels.AuthorDaoModel;
import io.tstud.paperweight.Model.DaoModels.BookDaoModel;

@androidx.room.Database(entities = {BookDaoModel.class, AuthorDaoModel.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract BookDao bookDao();
}
