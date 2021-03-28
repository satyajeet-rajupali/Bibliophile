package com.satyajeet.bibliophile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class BookList extends AppCompatActivity {


   // private String GOOGLE_BOOKS_SITE = "https://www.googleapis.com/books/v1/volumes?q=cpp&maxResults=40";
    private static AccessibilityService context;
    private ArrayList<BookModel> bookModel;

    RecyclerView rcv;
    GridLayoutManager gridLayoutManager;
    BookAdapter bookAdapter;

    String thumbnails = "";
    private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        rcv = findViewById(R.id.items);
        progressBar = findViewById(R.id.loading);
        bookModel = new ArrayList<>();

        Bundle receivedQuery = getIntent().getExtras();
        String query = receivedQuery.getString("searchquery");
        Log.e("BookList", "Searched query: " + query);

        String GOOGLE_BOOKS_SITE = "https://www.googleapis.com/books/v1/volumes?q=" + query + "&maxResults=40";


        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        rcv.setLayoutManager(gridLayoutManager);
        getData(GOOGLE_BOOKS_SITE);
    }

    public static String addCharToString(String str, char c, int pos) {
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.insert(pos, c);
        return stringBuilder.toString();
    }

    public static String writersNames(ArrayList<String> auth1){
        String athrName = "";
        for (int i=0; i<auth1.size(); i++){
            athrName = athrName + auth1.get(i) + ", ";
        }
        athrName = athrName.substring(0,athrName.length()-2);
        return athrName + "." ;
    }




    public void getData(String url){
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



                try {
                    JSONArray results = response.getJSONArray("items");
                    for(int i=0; i<results.length(); i++){

                        JSONObject items = results.getJSONObject(i);
                        JSONObject volumeInfo = items.getJSONObject("volumeInfo");
                        String title = volumeInfo.getString("title");
                        ArrayList<String> authors_Name = new ArrayList<>();
                        String author_name = "";
                        if(volumeInfo.has("authors")) {

                            JSONArray authors = volumeInfo.getJSONArray("authors");
                            if(authors.length() == 1) {
                                author_name = authors.getString(0);
                            } else {
                                for(int s=0; s<authors.length(); s++){
                                    authors_Name.add(authors.getString(s));
                                }
                                author_name = writersNames(authors_Name);
                            }
                        } else{
                            author_name = "N/A";
                        }

                        if(volumeInfo.has("imageLinks")) {
                            JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                            thumbnails = imageLinks.getString("thumbnail");
                            thumbnails = addCharToString(thumbnails, 's',4);
                            Log.e("BookList", "Image link: " + thumbnails);
                        } else {
                            thumbnails = "https://image.shutterstock.com/shutterstock/photos/622639151/display_1500/stock-vector-black-linear-photo-camera-logo-like-no-image-available-flat-stroke-style-trend-modern-logotype-art-622639151.jpg";
                        }
                        String description = "";
                        if (volumeInfo.has("description")){
                            description = volumeInfo.getString("description");
                        } else{
                            description = "No Description Available";
                        }


                        bookModel.add(new BookModel(title, author_name, thumbnails, description));
                        Log.e("BookList: ", "Size of data fetched: " + bookModel.size());
                    }


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "You encountered an exception", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                bookAdapter = new BookAdapter(bookModel, getApplicationContext());
                rcv.setAdapter(bookAdapter);
                progressBar.setVisibility(View.GONE);


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "You encountered an error", Toast.LENGTH_LONG).show();

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.boo_list, new NoInternetConnection()).addToBackStack(null).commit();




                } else if (error instanceof AuthFailureError) {
                    // Error indicating that there was an Authentication Failure while performing the request
                    Toast.makeText(getApplicationContext(), "This error is case2", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    //Indicates that the server responded with a error response
                    Toast.makeText(getApplicationContext(), "This error is server error", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    //Indicates that there was network error while performing the request
                    Toast.makeText(getApplicationContext(), "This error is case4", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    // Indicates that the server response could not be parsed
                    Toast.makeText(getApplicationContext(), "This error is case5", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "An unknown error occurred.", Toast.LENGTH_SHORT).show();
                }



            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}