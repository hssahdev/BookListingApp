package com.harshdeep.android.booklistingapp;

import android.graphics.Bitmap;

/**
 * Created by harshdeepsingh on 20/12/17.
 */

public class Book {
    private String author;
    private String name;
    private Bitmap image;
    private String getInfoURL;

    public Book(String author, String name) {
        this.author = author;
        this.name = name;
    }

    public Book(String author, String name, Bitmap image) {
        this.author = author;
        this.name = name;
        this.image = image;

    }

    public Book(String author, String name, Bitmap imageURL, String getInfoURL) {
        this.author = author;
        this.name = name;
        this.image = imageURL;
        this.getInfoURL = getInfoURL;

    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getGetInfoURL() {
        return getInfoURL;
    }
}
