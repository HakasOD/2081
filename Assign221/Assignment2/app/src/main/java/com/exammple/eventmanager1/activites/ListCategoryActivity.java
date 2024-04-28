package com.exammple.eventmanager1.activites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.exammple.eventmanager1.R;

public class ListCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Category List");
        setSupportActionBar(toolbar);

        // Add back button
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Inflate category headings
        CardView cardView = findViewById(R.id.cardView);
        cardView.setFocusable(false);
        cardView.setFocusableInTouchMode(false);
        LayoutInflater inflater = LayoutInflater.from(this);
        View cardLayout = inflater.inflate(R.layout.category_list_headings_card, cardView, false);
        cardView.addView(cardLayout);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);

    }
}