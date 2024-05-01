package com.exammple.eventmanager1.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.exammple.eventmanager1.KeyStore;
import com.exammple.eventmanager1.R;

public class MainActivity extends AppCompatActivity {
    EditText editTextConfirmPassword, editTextPassword, editTextUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

    }

    public void onSignUpButtonClick(View view)
    {
        boolean successfulRegistration = false;

        // Get user input values
        String userName = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        // Input values must not be empty & confirm password and password must match
        if (password.equals(confirmPassword) &&
                !userName.isEmpty() &&
                !password.isEmpty())
        {
            successfulRegistration = true;
        }


        if(successfulRegistration)
        {
            // Save all values in shared preferences
            saveDataToSharedPreferences(userName, password);

            // Go to log in page
            Toast.makeText(this, "Successfully registered", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ShowLogInPage.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Unsuccessful sign up attempt", Toast.LENGTH_SHORT).show();
        }

    }

    public void onLoginButtonClick(View view)
    {
        // Go to log in page
        Intent intent = new Intent(this, ShowLogInPage.class);
        startActivity(intent);
    }

    private void saveDataToSharedPreferences(String userName, String password)
    {
        // Save all values in shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.LOGIN_DATA_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KeyStore.USERNAME_KEY, userName);
        editor.putString(KeyStore.PASSWORD_KEY, password);
        editor.apply();

    }

    public static int countChar(String str, char character)
    {
        int count = 0;

        for (char c : str.toCharArray())
        {
            if(c == character)
            {
                count++;
            }
        }

        return count;
    }
}