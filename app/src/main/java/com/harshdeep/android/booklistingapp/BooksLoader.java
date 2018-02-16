package com.harshdeep.android.booklistingapp;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by harshdeepsingh on 21/12/17.
 */

public class BooksLoader extends android.support.v4.content.AsyncTaskLoader {

    private String url;

    public BooksLoader(Context context, String url1) {
        super(context);
        url=url1;
    }

    @Override
    public ArrayList loadInBackground() {

        if(url==null)
            return null;

        return NetworkUtility.fetchData(url);
    }
}
