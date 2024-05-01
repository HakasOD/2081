package com.exammple.eventmanager1.activites;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.exammple.eventmanager1.R;
import com.exammple.eventmanager1.provider.Category;
import com.exammple.eventmanager1.provider.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Random;

public class DashboardActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FloatingActionButton fab;
    NavigationView navigationView;
    private EditText editTextEventId, editTextCategoryId, editTextEventName, editTextTicketsAvailable;
    private Switch switchIsActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        editTextEventId = findViewById(R.id.editTextEventId);
        editTextCategoryId = findViewById(R.id.editTextCategoryIdEventForm);
        editTextEventName = findViewById(R.id.editTextEventName);
        editTextTicketsAvailable = findViewById(R.id.editTextTicketsAvailable);
        switchIsActive = findViewById(R.id.switchIsActiveEventForm);

        // Add drawer bar toggle to action bar
        Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("Assignment 2");
        drawerLayout = findViewById(R.id.draw);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, myToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());
        setSupportActionBar(myToolbar);

        // Drawer icon is always enabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //FAB bar
        fab = findViewById(R.id.fab);
        setFabOnClickListener(fab);

        // Inflate category headings
        CardView cardView = findViewById(R.id.categoryHeadings);
        cardView.setFocusable(false);
        cardView.setFocusableInTouchMode(false);
        LayoutInflater inflater = LayoutInflater.from(this);
        View cardLayout = inflater.inflate(R.layout.category_list_headings_card, cardView, false);
        cardView.addView(cardLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Open navigation drawer
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        else if (item.getItemId() == R.id.option_refresh) {
            //todo reimplement refresh button
        }
        else if (item.getItemId() == R.id.option_clear_event_form) {
            clearFields();
        }
        else if(item.getItemId() == R.id.option_delete_all_categories){
            //todo reimplement delete all categories
        }
        else if(item.getItemId() == R.id.option_delete_all_events){
            //todo reimplement delete all events
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Clears all edit texts within this activity
     */
    private void clearFields() {
        editTextCategoryId.getText().clear();
        editTextEventId.getText().clear();
        editTextEventName.getText().clear();
        editTextTicketsAvailable.getText().clear();
        switchIsActive.setChecked(false);
    }

    private boolean isValidEventName(String eventName){
        //Cannot only be numbers
        if(eventName.matches("[0-9]+")){
            return false;
        }

        // Can be alphanumeric and space
        String validCharacters = "^[a-zA-Z0-9 ]+$";
        return eventName.matches(validCharacters);
    }

    private String generateEventId() {
        StringBuilder eventId = new StringBuilder();
        Random rand = new Random();

        // 2 random chars
        char c1 = (char) (rand.nextInt(26) + 'A');
        char c2 = (char) (rand.nextInt(26) + 'A');

        // Random 5 digit number
        int randomNumber = rand.nextInt(90000) + 10000;

        eventId.append("E");
        eventId.append(c1);
        eventId.append(c2);
        eventId.append("-");
        eventId.append(randomNumber);

        return eventId.toString();
    }

    private void setFabOnClickListener(FloatingActionButton fab){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // todo reimplemnt fab onclick
            }
        });
    }

    //todo reimplement saving to event database
//    private boolean saveFieldsToEventDatabase() {
//        // Get data from fields
//        String categoryId = editTextCategoryId.getText().toString();
//        String eventName = editTextEventName.getText().toString();
//        boolean isActive = switchIsActive.isChecked();
//        String ticketsAvailableString = editTextTicketsAvailable.getText().toString();
//        int ticketsAvailable;
//        if (ticketsAvailableString.isEmpty()) {
//            ticketsAvailable = 0;
//        } else ticketsAvailable = Integer.parseInt(ticketsAvailableString);
//
//
//        return true;
//    }

    private boolean validFields(String categoryId, String eventName, int ticketsAvailable) {
        //todo reimplement validate fields
        //Get category id from database


        boolean existingCategoryId = false;
        boolean validName = false;
        boolean validTicketsAvailable = false;

//        // Entered categoryId must match an existing one
//        for (Category category : db) {
//            if (category.getCategoryId().equals(categoryId)) {
//                existingCategoryId = true;
//            }
//        }

        if (isValidEventName(eventName))
        {
            validName = true;
        }

        if(ticketsAvailable >= 0){
            validTicketsAvailable = true;
        }

        if(!validName){
            Toast.makeText(this, "Invalid event name",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!validTicketsAvailable) {
            Toast.makeText(this, "Invalid tickets available",
                    Toast.LENGTH_SHORT).show();
        } else if(!existingCategoryId) {
            Toast.makeText(this, "Category does not exist",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void undoSave(Event event){
        //todo reimplement undoSave
//
//        // Update event count of corresponding Category
//        ArrayList<Category> categoryDb = DatabaseManagement.
//                getCategoryDatabaseFromSharedPreferences(this);
//        for(Category category : categoryDb){
//            if (category.getCategoryId().equals(event.getCategoryId())) {
//                category.setEventCount(category.getEventCount() - 1);
//                DatabaseManagement.
//                        saveCategoryDatabaseToSharedPreferences(this,categoryDb);
//            }
//        }
    }



    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int id = menuItem.getItemId();

            if(id == R.id.item_add_category){
                Intent intent = new Intent(DashboardActivity.this, NewEventCategoryActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_view_all_categories) {
                Intent intent = new Intent(DashboardActivity.this, ListCategoryActivity.class);
                startActivity(intent);
            } else if (id == R.id.item_view_all_events) {
                Intent intent = new Intent(DashboardActivity.this, ListEventActivity.class);
                startActivity(intent);
            } else if (id == R.id.item_logout) {
                Intent intent = new Intent(DashboardActivity.this, ShowLogInPage.class);
                startActivity(intent);
                finish(); //todo make it so you cant go back on log out
            }
            drawerLayout.closeDrawers();
            return true;
        }
    }
}