package com.satyajeet.bibliophile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText search_input;
    Button search_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search_input = findViewById(R.id.edt_txt_search);
        search_button = findViewById(R.id.search);





        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookList.class);
                Bundle searchQuery = new Bundle();
                String search = search_input.getText().toString();
                searchQuery.putString("searchquery", search);
                Log.e("MainActivity", "MainActivity Searched query: " + search);
                intent.putExtras(searchQuery);
                startActivity(intent);
            }
        });

    }


}