package com.exammple.eventmanager1.fragments;

import static android.app.ProgressDialog.show;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.exammple.eventmanager1.appmanagement.DatabaseManagement;
import com.exammple.eventmanager1.entities.Category;
import com.exammple.eventmanager1.entities.Event;
import com.exammple.eventmanager1.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFormFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    EditText editTextEventId, editTextCategoryId, editTextEventName, editTextTicketsAvailable;
    Switch switchIsActive;
    ArrayList<Event> db;



    public EventFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventFormFragment.
     */
    public static EventFormFragment newInstance(String param1, String param2) {
        EventFormFragment fragment = new EventFormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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


    public void clearFields() {
        editTextCategoryId.getText().clear();
        editTextEventId.getText().clear();
        editTextEventName.getText().clear();
        editTextTicketsAvailable.getText().clear();
        switchIsActive.setChecked(false);
    }


    public String generateEventId() {
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

    private boolean validFields(String categoryId, String eventName, int ticketsAvailable) {
        //Get category id from database
        ArrayList<Category> db = DatabaseManagement.
                getCategoryDatabaseFromSharedPreferences(getContext());

        boolean existingCategoryId = false;
        boolean validName = false;
        boolean validTicketsAvailable = false;

        // Entered categoryId must match an existing one
        for (Category category : db) {
            if (category.getCategoryId().equals(categoryId)) {
                existingCategoryId = true;
            }
        }

        if (isValidEventName(eventName))
        {
            validName = true;
        }

        if(ticketsAvailable >= 0){
            validTicketsAvailable = true;
        }

        if(!validName){
            Toast.makeText(getContext(), "Invalid event name",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!validTicketsAvailable) {
            Toast.makeText(getContext(), "Invalid tickets available",
                    Toast.LENGTH_SHORT).show();
        } else if(!existingCategoryId) {
            Toast.makeText(getContext(), "Category does not exist",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public boolean isValidEventName(String eventName){
        //Cannot only be numbers
        if(eventName.matches("[0-9]+")){
            return false;
        }

        // Can be alphanumeric and space
        String validCharacters = "^[a-zA-Z0-9 ]+$";
        return eventName.matches(validCharacters);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_event_form, container, false);

        super.onCreate(savedInstanceState);

        editTextEventId = v.findViewById(R.id.editTextEventId);
        editTextCategoryId = v.findViewById(R.id.editTextCategoryIdEventForm);
        editTextEventName = v.findViewById(R.id.editTextEventName);
        editTextTicketsAvailable = v.findViewById(R.id.editTextTicketsAvailable);
        switchIsActive = v.findViewById(R.id.switchIsActiveEventForm);

        if(getContext() != null) {
            db = DatabaseManagement.getEventDatabaseFromSharedPreferences(getContext());
        }

        return v;
    }


}