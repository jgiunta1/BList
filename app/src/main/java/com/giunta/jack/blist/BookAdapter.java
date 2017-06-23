package com.giunta.jack.blist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jack on 6/20/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    private static final String LOG_TAG = BookAdapter.class.getSimpleName();

    public BookAdapter(Activity context, ArrayList<Book> books){
        super(context, 0 , books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        // Get the {@link Book} object located at this position in the list
        Book currentBook = getItem(position);

        // book_rating
        TextView rating = (TextView) listItemView.findViewById(R.id.book_rating);
        Double ratingDouble = currentBook.getAverageRating();
        String formattedRating = formatRating(ratingDouble);
        rating.setText(formattedRating);

        // book_title
        TextView title = (TextView) listItemView.findViewById(R.id.book_title);
        title.setText(currentBook.getTitle());

        // book_date
        TextView date = (TextView) listItemView.findViewById(R.id.book_date);
        String formattedDate = formatDate(currentBook.getDate());
        date.setText(formattedDate);

        // book_author
        TextView author = (TextView) listItemView.findViewById(R.id.book_author);
        String authors = formatAuthor((ArrayList<String>) currentBook.getAuthors());
        author.setText(authors);

        return listItemView;
    }

    private String formatRating(Double rating){
        if(rating == null){
            return "NA";
        }
        DecimalFormat rateFormat = new DecimalFormat("0.0");
        return rateFormat.format(rating);
    }

    private String formatDate(Date date){
        if(date == null)
            return "NA";
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(date);
    }

    private String formatAuthor(ArrayList<String> authors){

        if(authors == null || authors.isEmpty()){
            return "Author Not Available";
        }
        StringBuilder authorsBuilder = new StringBuilder();
        for(int i = 0; i < authors.size() - 1; i++){
            authorsBuilder.append(authors.get(i) + ", ");
        }
        authorsBuilder.append(authors.get(authors.size() - 1));


        return authorsBuilder.toString();
    }
}
