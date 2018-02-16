package com.harshdeep.android.booklistingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by harshdeepsingh on 20/12/17.
 */

public class BookAdapter extends ArrayAdapter<Book>{


    public BookAdapter(@NonNull Context context, ArrayList<Book> books) {
        super(context,0, books);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.books_view,parent,false);
        }

        View listView=convertView;

        final Book currentbook=getItem(position);


        ImageView booksImage=listView.findViewById(R.id.bookImage);
        booksImage.setImageBitmap(currentbook.getImage());

        final Button infoButton=listView.findViewById(R.id.getInfoButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(currentbook.getGetInfoURL());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    getContext().startActivity(intent);
                }
                Log.v("image clicked","of "+position);
            }
        });

        TextView bookName=listView.findViewById(R.id.bookName);
        bookName.setText(currentbook.getName());

        TextView authorName=listView.findViewById(R.id.author);
        authorName.setText(currentbook.getAuthor());

        return listView;
    }

}
