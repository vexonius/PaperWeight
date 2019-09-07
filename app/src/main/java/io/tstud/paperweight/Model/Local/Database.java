package io.tstud.paperweight.Model.Local;


import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import io.tstud.paperweight.Model.Dao.BookDao;
import io.tstud.paperweight.Model.Dao.CurrentlyReadingDao;
import io.tstud.paperweight.Model.Models.CurrentlyReadingStats;
import io.tstud.paperweight.Model.Models.Item;

@androidx.room.Database(entities = {Item.class, CurrentlyReadingStats.class}, version = 1, exportSchema = false)
@TypeConverters(CustomTypeConverters.class)
public abstract class Database extends RoomDatabase {
    private volatile static Database instance;
    private static final String DB_NAME = "paperbase";

    public abstract BookDao bookDao();
    public abstract CurrentlyReadingDao currentlyReadingDao();

    public static void setInstance(final Context context){
        if(instance == null){
            synchronized (Database.class){
                instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, DB_NAME).build();
            }
        }
    }

    public static Database getInstance(){
        return instance;
    }


}
