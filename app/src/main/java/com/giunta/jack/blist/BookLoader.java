package com.giunta.jack.blist;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/**
 * Created by Jack on 6/22/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String keyword;
    public static final String LOG_TAG = "BOOKLOADER";

    public BookLoader(Context context, String keyword) {
        super(context);
        this.keyword = keyword;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "onStartLoading() Called");
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {

        if(TextUtils.isEmpty(keyword))
            return null;

        List<Book> result = QueryUtils.fetchBookData(keyword);
        Log.i(LOG_TAG, "loadInBackground() called...");
        return null;
    }


}
