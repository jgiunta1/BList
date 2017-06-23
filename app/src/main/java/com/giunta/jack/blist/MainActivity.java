package com.giunta.jack.blist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static EditText keywordView;
    private BookAdapter bookAdapter;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find reference to EmptyTextView
        emptyView = (TextView)findViewById(R.id.empty_state_TextView);

        // Find reference to {@link ListView} in the layout
        ListView bookListView = (ListView)findViewById(R.id.bookList);
        bookListView.setEmptyView(emptyView);

        // Create a new adapter with an empty list of Books
        bookAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(bookAdapter);

        // Find EditText view
        keywordView = (EditText) findViewById(R.id.keyword);

    }

    public void searchBooks(View v){
        String keyword = keywordView.getText().toString();

        // Temporarily Load Sample Data into ListView
        bookAdapter.clear();
        List<String> authors = new ArrayList<>();
        authors.add("Author1");
        authors.add("Author2");
        authors.add("Author3");
        Book b1 = new Book("Title1", 4, new Date(), authors);
        Book b2 = new Book("Title2", 3, new Date(), authors);
        Book b3 = new Book("Title3", 1, new Date(), authors);

        List<Book> data = new ArrayList<>();
        data.add(b1);
        data.add(b2);
        data.add(b3);

        bookAdapter.addAll(data);
    }
}
