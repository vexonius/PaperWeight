package io.tstud.paperweight.Model.DaoModels;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// todo: delete this

@Entity(tableName = "books")
public class BookDaoModel {

    @PrimaryKey
    private String id;

    private String title;

    private String subtitle;

    private String publisher;

    private String description;

    private String publishedDate;

    private int pageCount;

    private double averageRating;

    private int ratingsCount;

    private String maturityRating;

    private String language;

    public String getDescription() {
        return description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getId() {
        return id;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getTitle() {
        return title;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public String getLanguage() {
        return language;
    }

    public String getMaturityRating() {
        return maturityRating;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setMaturityRating(String maturityRating) {
        this.maturityRating = maturityRating;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }
}
