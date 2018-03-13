package com.harshdeep.android.booklistingapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by harshdeepsingh on 20/12/17.
 */

public class NetworkUtility {
    private NetworkUtility(){}

    public static ArrayList fetchData(String fakeurl){
        URL url = createURL(fakeurl);
        String JSON_Response="";

        try {
             JSON_Response=setupConnectionAndGetJSON(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parseJSON(JSON_Response);

    }

    private static URL createURL(String text){
        URL url=null;

        try {
            url=new URL(text);
        } catch (MalformedURLException e) {
            Log.e("in NetworkUtility","Malformed URL",e);
            e.printStackTrace();
        }

        return url;
    }

    private static  String setupConnectionAndGetJSON(URL url) throws IOException {

        InputStream inputStream;
        String Json="";

        HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setConnectTimeout(10000);
        urlConnection.setReadTimeout(15000);
        urlConnection.connect();

        if(urlConnection.getResponseCode()==200){
            inputStream=urlConnection.getInputStream();
            Json=readInputStream(inputStream);

        }
        else return Json;

        if (urlConnection!=null)
            urlConnection.disconnect();
        if (inputStream!=null)
            inputStream.close();
        return Json;

    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder builder=new StringBuilder();
        InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        String line=bufferedReader.readLine();
        while (line!=null){
            builder.append(line);
            line=bufferedReader.readLine();
        }

        return builder.toString();
    }

    private static ArrayList<Book> parseJSON(String JsonString){
        ArrayList<Book> bookArrayList=null;
        JSONObject rootJSON;

        if(JsonString==null)
            return null;
        try {
             rootJSON=new JSONObject(JsonString);
            JSONArray getItems=rootJSON.getJSONArray("items");
            if(getItems==null)
                return bookArrayList;

            bookArrayList=new ArrayList<>();
            for (int i=0;i<getItems.length();i++){
                String finalAuthors;
                JSONObject VolumeInfo=getItems.getJSONObject(i).getJSONObject("volumeInfo");

                String BookTitle=VolumeInfo.getString("title");

                JSONArray authors= VolumeInfo.getJSONArray("authors");
                if(authors.length()==1)
                    finalAuthors=authors.getString(0);
                else{
                    StringBuilder builder=new StringBuilder();
                    for (int j=0;j<authors.length()-1;j++){
                        builder.append(authors.getString(j)+", ");
                    }
                    builder.append(authors.getString(authors.length()-1));
                    finalAuthors=builder.toString();
                }
                //Send http request to get image bitmap

                String imageURL=VolumeInfo.getJSONObject("imageLinks").getString("smallThumbnail");

                // Info links

                String bookURL =VolumeInfo.getString("infoLink");

                bookArrayList.add(new Book(finalAuthors,BookTitle,imageURL,bookURL));

                Log.v("Var",finalAuthors+" Title: "+BookTitle +" URL: "+bookURL);
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
        return bookArrayList;
    }
}
