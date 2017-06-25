package com.giunta.jack.blist;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
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
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find reference to EmptyTextView and ProgressBar
        emptyView = (TextView) findViewById(R.id.empty_state_TextView);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // Check for network connectivity
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            // Find reference to {@link ListView} in the layout
            ListView bookListView = (ListView) findViewById(R.id.bookList);
            bookListView.setEmptyView(emptyView);

            // Create a new adapter with an empty list of Books
            bookAdapter = new BookAdapter(this, new ArrayList<Book>());
            bookListView.setAdapter(bookAdapter);

            // Find EditText view
            keywordView = (EditText) findViewById(R.id.keyword);
        } else {
            Button search = (Button) findViewById(R.id.button_search);
            search.setClickable(false);
            emptyView.setText("No Internet Connection");
        }

    }

    public void searchBooks(View v) {
        keyword = keywordView.getText().toString();

        if (TextUtils.isEmpty(keyword)) {
            Toast.makeText(MainActivity.this, "INVALID KEYWORD", Toast.LENGTH_LONG).show();
            return;
        }

        getLoaderManager().initLoader(BOOK_LOADER_ID, null, this);

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Show Loading Bar
        progressBar.setVisibility(View.VISIBLE);
        return new BookLoader(this, keyword);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        progressBar.setVisibility(View.GONE);
        emptyView.setText("No Results");

        bookAdapter.clear();

        if (books != null && !books.isEmpty()) {
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
