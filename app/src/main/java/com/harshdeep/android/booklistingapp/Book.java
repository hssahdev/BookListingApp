package com.harshdeep.android.booklistingapp;

/**
 * Created by harshdeepsingh on 20/12/17.
 */

public class Book {
    private String author;
    private String name;
    private String imageURL;
    private String getInfoURL;

    public Book(String author, String name) {
        this.author = author;
        this.name = name;
    }

    public Book(String author, String name, String image) {
        this.author = author;
        this.name = name;
        this.imageURL = image;

    }

    public Book(String author, String name, String imageURL, String getInfoURL) {
        this.author = author;
        this.name = name;
        this.imageURL = imageURL;
        this.getInfoURL = getInfoURL;

    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getGetInfoURL() {
        return getInfoURL;
    }
}
