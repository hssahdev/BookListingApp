package com.harshdeep.android.booklistingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList>{

    private String BASE_BOOKS_API_URL="https://www.googleapis.com/books/v1/volumes?q=";
    private String FINAL_URL=null;
    private BookAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View progress=findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);
        ImageView arrowButton=findViewById(R.id.arrowButton);
        final ListView listView=findViewById(R.id.listView);

        if (getSupportLoaderManager().getLoader(1)!=null){
        getSupportLoaderManager().initLoader(1,null,this);
            Log.v("ma","loader not null");}


        arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText getKey=findViewById(R.id.Key);
                String key=getKey.getText().toString();

                progress.setVisibility(View.VISIBLE);
                InputMethodManager manager=(InputMethodManager) MainActivity.this.getSystemService(INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                FINAL_URL=BASE_BOOKS_API_URL+key;

                ConnectivityManager connectivityManager=(ConnectivityManager)MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
                boolean isConnected = networkInfo!=null && networkInfo.isConnectedOrConnecting();
                        if(isConnected){
                            getSupportLoaderManager().restartLoader(1,null,MainActivity.this).forceLoad();
                        }
                else {      if(adapter!=null)
                            adapter.clear();
                            progress.setVisibility(View.GONE);
                            TextView textView= findViewById(R.id.textView);
                            textView.setText("Connect to Internet..");

                        }
                listView.setEmptyView(findViewById(R.id.emptyView));

            }
        });



    }


    @Override
    public Loader<ArrayList> onCreateLoader(int id, Bundle args) {
        return new BooksLoader(this,FINAL_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList> loader, ArrayList data) {


            final View progress=findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);

        if (data==null)
        {
            adapter.clear();
            TextView textView= findViewById(R.id.textView);
            textView.setText("No Books Found.");
            return;
        }
                ListView listView=findViewById(R.id.listView);
                listView.setEmptyView(findViewById(R.id.emptyView));



                adapter=new BookAdapter(MainActivity.this,data);
                listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trying,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.app_bar_switch)
            Toast.makeText(this, "Hey", Toast.LENGTH_SHORT).show();
        return true;
    }
}


