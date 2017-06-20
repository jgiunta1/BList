package com.giunta.jack.blist;

import java.util.List;

/**
 * Created by Jack on 6/20/2017.
 */

public class Book {

    public String title;
    public String date;
    public List<String> authors;

    public Book(String title, String date, List<String> authors){
        this.title = title;
        this.date = date;
        this.authors = authors;
    }
}
