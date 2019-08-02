package io.tstud.paperweight.Model.DaoModels;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// todo: delete this

@Entity(tableName = "authors")
public class AuthorDaoModel {

    @PrimaryKey
    private int id;

    @ColumnInfo( name = "name")
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
