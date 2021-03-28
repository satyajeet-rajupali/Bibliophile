package com.satyajeet.bibliophile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Objects;

public class BookDescrption extends AppCompatActivity {

    private TextView bookTitle, authors, description;
    private String BT, ATHRS, D, image;
    private ImageView bookcover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_descrption);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        bookcover = findViewById(R.id.bookcover);
        bookTitle = findViewById(R.id.book_title);
        authors = findViewById(R.id.authors);
        description = findViewById(R.id.description);

        Bundle des = getIntent().getExtras();

        BT = des.getString("title");
        ATHRS = des.getString("authors");
        D = des.getString("description");
        image = des.getString("imageLink");

        bookTitle.setText(BT);
        authors.setText("Authors: " + ATHRS);
        description.setText(D);

        Picasso.get().load(image).into(bookcover);
    }
}