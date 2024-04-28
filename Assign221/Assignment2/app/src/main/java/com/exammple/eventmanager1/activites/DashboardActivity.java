package com.exammple.eventmanager1.activites;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.exammple.eventmanager1.appmanagement.DatabaseManagement;
import com.exammple.eventmanager1.R;
import com.exammple.eventmanager1.entities.Category;
import com.exammple.eventmanager1.entities.Event;
import com.exammple.eventmanager1.fragments.EventFormFragment;
import com.exammple.eventmanager1.fragments.FragmentListCategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

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

        Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("Assignment 2");
        // Add drawer bar toggle to action bar
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

    /**
     * Clears all edit texts within this activity
     */
    private void clearFields() {
        //todo get edit texts
    }

    public boolean saveFieldsToEventDatabase() {
        // Get data from fields
        String categoryId = editTextCategoryId.getText().toString();
        String eventName = editTextEventName.getText().toString();
        boolean isActive = switchIsActive.isChecked();
        String ticketsAvailableString = editTextTicketsAvailable.getText().toString();
        int ticketsAvailable;
        if (ticketsAvailableString.isEmpty()) {
            ticketsAvailable = 0;
        } else ticketsAvailable = Integer.parseInt(ticketsAvailableString);

        // Save data to database
        if (validFields(categoryId, eventName, ticketsAvailable)) {
            // Generate event ID
            String eventId = generateEventId();
            editTextEventId.setText(eventId);

            db = DatabaseManagement.getEventDatabaseFromSharedPreferences(getContext());

            // Add event to db and save to shared preferences
            Event event = new Event(eventId, categoryId, eventName, ticketsAvailable, isActive);
            db.add(event);
            DatabaseManagement.saveEventDatabaseToSharedPreferences(getContext(), db);

            // Update event count of corresponding Category
            ArrayList<Category> categoryDb = DatabaseManagement.
                    getCategoryDatabaseFromSharedPreferences(getContext());
            for (Category category : categoryDb) {
                // Add one to the event count if category id matches
                if (category.getCategoryId().equals(db.get(db.size() - 1).getCategoryId())) {
                    category.setEventCount(category.getEventCount() + 1);
                    DatabaseManagement.saveCategoryDatabaseToSharedPreferences(getContext(), categoryDb);
                }
            }

            return true;
        }
        return false;
    }


    private void setFabOnClickListener(FloatingActionButton fab){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //todo get rid of event form fragment


//                // Show snackbar with undo action
//                if(fragment.saveFieldsToEventDatabase(v)) {  // If event form is successfully saved
//                   // Get the saved event
//                    ArrayList<Event> eventDb = DatabaseManagement.
//                            getEventDatabaseFromSharedPreferences(v.getContext());
//                    Event mostRecentSavedEvent = eventDb.get(eventDb.size() - 1);
//
//                    Snackbar.make(v, "Event " + mostRecentSavedEvent.getEventId() + " saved to category " +
//                                    mostRecentSavedEvent.getCategoryId(), Snackbar.LENGTH_LONG)
//                            .setAction("Undo", c -> undoSave(mostRecentSavedEvent)).show();
//
//                    // Refresh the category list fragment to reflect any changes
//                    FragmentListCategory categoryFragment = (FragmentListCategory) getSupportFragmentManager()
//                            .findFragmentById(R.id.fragmentContainerDashboardListCategory);
//
//                    categoryFragment.updateRecyclerView();
//                }
            }
        });
    }

    public void undoSave(Event event){
        DatabaseManagement.removeLastElementFromEventDatabase(this);

        // Update event count of corresponding Category
        ArrayList<Category> categoryDb = DatabaseManagement.
                getCategoryDatabaseFromSharedPreferences(this);
        for(Category category : categoryDb){
            if (category.getCategoryId().equals(event.getCategoryId())) {
                category.setEventCount(category.getEventCount() - 1);
                DatabaseManagement.
                        saveCategoryDatabaseToSharedPreferences(this,categoryDb);
            }
        }

        // Update ui
        FragmentListCategory categoryFragment = (FragmentListCategory) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerDashboardListCategory);
        categoryFragment.updateRecyclerView();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Open navigation drawer
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        else if (item.getItemId() == R.id.option_refresh) {
            // Reload category list
            FragmentListCategory fragment = (FragmentListCategory) getSupportFragmentManager()
                    .findFragmentById(R.id.fragmentContainerDashboardListCategory);
            if (fragment != null) {
                fragment.updateCategoryToDatabase();
            }
            recreate();
        }
        else if (item.getItemId() == R.id.option_clear_event_form) {
            clearFields();
        }
        else if(item.getItemId() == R.id.option_delete_all_categories){
            DatabaseManagement.clearCategoriesDatabase(this);

            // Update category fragment UI
            FragmentListCategory fragment = (FragmentListCategory) getSupportFragmentManager()
                    .findFragmentById(R.id.fragmentContainerDashboardListCategory);

            if (fragment != null){
                fragment.updateRecyclerView();
            }
        }
        else if(item.getItemId() == R.id.option_delete_all_events){
            // Update event count in corresponding categories
            ArrayList<Event> eventDb = DatabaseManagement.
                    getEventDatabaseFromSharedPreferences(this);
            ArrayList<Category> categoryDb = DatabaseManagement.
                    getCategoryDatabaseFromSharedPreferences(this);
            for(Event event : eventDb){
                for(Category category : categoryDb) {
                    if (event.getCategoryId().equals(category.getCategoryId())){
                        category.setEventCount(category.getEventCount() - 1);
                        DatabaseManagement.
                                saveCategoryDatabaseToSharedPreferences(this, categoryDb);
                    }

                }
            }

            DatabaseManagement.clearEventDatabase(this);

            // Update category fragment UI
            FragmentListCategory fragment = (FragmentListCategory) getSupportFragmentManager()
                    .findFragmentById(R.id.fragmentContainerDashboardListCategory);

            if (fragment != null){
                fragment.updateRecyclerView();
            }
        }

        return super.onOptionsItemSelected(item);
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
                finish();
            }
            drawerLayout.closeDrawers();
            return true;
        }
    }
}