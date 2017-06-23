package com.giunta.jack.blist;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {
    public static final String LOG_TAG = "MAIN";
    private static final int BOOK_LOADER_ID = 1;
    private static EditText keywordView;
    private BookAdapter bookAdapter;
    private static String keyword;
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
        keyword = keywordView.getText().toString();

        if(TextUtils.isEmpty(keyword)){
            Toast.makeText(MainActivity.this, "INVALID KEYWORD", Toast.LENGTH_LONG).show();
            return;
        }

        // TODO Show loading bar


        getLoaderManager().initLoader(BOOK_LOADER_ID, null, this);


        // Temporarily Load Sample Data into ListView
/*        bookAdapter.clear();
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

        bookAdapter.addAll(data);*/
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, keyword);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        emptyView.setText("No Results");

        bookAdapter.clear();

        if(books != null && !books.isEmpty()){
            bookAdapter.addAll(books);
        } else {
            Log.e(LOG_TAG, "Error Populating List");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        bookAdapter.clear();
    }
}
