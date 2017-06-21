package com.giunta.jack.blist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
        double ratingDouble = currentBook.getAverageRating();
        String formattedRating = formatRating(ratingDouble);
        rating.setText(formattedRating);

        // book_title
        TextView title = (TextView) listItemView.findViewById(R.id.book_title);
        title.setText(currentBook.getTitle());

        // book_date
        TextView date = (TextView) listItemView.findViewById(R.id.book_date);

        return listItemView;
    }

    private String formatRating(double rating){
        DecimalFormat rateFormat = new DecimalFormat("0.0");
        return rateFormat.format(rating);
    }
}
