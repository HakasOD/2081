package com.exammple.eventmanager1.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.exammple.eventmanager1.appmanagement.DatabaseManagement;
import com.exammple.eventmanager1.appmanagement.KeyStore;
import com.exammple.eventmanager1.R;
import com.exammple.eventmanager1.provider.Category;
import com.exammple.eventmanager1.provider.EventManagerViewModel;

import java.util.ArrayList;
import java.util.Random;

public class NewEventCategoryActivity extends AppCompatActivity {
    EditText editTextCategoryId;
    EditText editTextCategoryName;
    EditText editTextEventCount;
    private EventManagerViewModel eventManagerViewModel;
    Switch switchIsActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_category);

        editTextCategoryId = findViewById(R.id.editTextCategoryId);
        editTextCategoryName = findViewById(R.id.editTextCategoryName);
        editTextEventCount = findViewById(R.id.editTextEventCount);
        switchIsActive = findViewById(R.id.switchIsActive);

        eventManagerViewModel = new ViewModelProvider(this).get(EventManagerViewModel.class);

    }

    public void onSaveEventCategoryButtonClick(View view) {
        String categoryNameString = editTextCategoryName.getText().toString();
        String eventCountString = editTextEventCount.getText().toString();
        boolean isActive = switchIsActive.isChecked();

        // Parse eventCount to int
        int eventCountInt = 0;
        if (!eventCountString.isEmpty()) {
            eventCountInt = Integer.parseInt(eventCountString);
        }

        if (isValidCategoryFields(categoryNameString, eventCountString)) {
            // Autocomplete category id
            String categoryId = generateCategoryId();
            editTextCategoryId.setText(categoryId);

            //Save category to database
            Category category = new Category(categoryId, categoryNameString, eventCountInt, isActive);
            eventManagerViewModel.insert(category);

            Toast.makeText(this, "Category " + categoryId + " successfully saved", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra("fromActivity", "NewEventCategory");
            startActivity(intent);
        }
    }

    public String generateCategoryId()
    {
        StringBuilder categoryId = new StringBuilder();
        Random rand = new Random();

        // 2 random chars
        char c1 = (char)(rand.nextInt(26) + 'A');
        char c2 = (char)(rand.nextInt(26) + 'A');

        // Random 4 digit number
        int randomNumber = rand.nextInt(9000) + 1000;

        categoryId.append("C");
        categoryId.append(c1);
        categoryId.append(c2);
        categoryId.append("-");
        categoryId.append(randomNumber);

        return categoryId.toString();
    }



    private boolean isValidCategoryFields(String categoryName, String eventCount)
    {
        if (!isValidCategoryName(categoryName)){
            Toast.makeText(this, "Invalid category name", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            if(eventCount.isEmpty() || Integer.parseInt(eventCount) > 0){
                return true;
            }
        }catch (Exception e)
        {
            Toast.makeText(this, "Invalid inputs", Toast.LENGTH_SHORT).show();
            return false;
        }

        return false;
    }
    private boolean isValidCategoryName(String categoryName){
       //Cannot only be numbers
        if(categoryName.matches("[0-9]+")){
            return false;
        }

        String validCharacters = "^[a-zA-Z0-9 ]+$";
        return categoryName.matches(validCharacters);
    }



}