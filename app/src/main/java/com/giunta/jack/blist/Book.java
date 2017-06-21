package com.giunta.jack.blist;

import java.util.Date;
import java.util.List;

/**
 * Created by Jack on 6/20/2017.
 */

public class Book {

    private String title;
    private double averageRating;
    private Date date;
    private List<String> authors;

    public Book(String title,double averageRating, Date date, List<String> authors){
        this.title = title;
        this.averageRating = averageRating;
        this.date = date;
        this.authors = authors;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }


}
