package com.exammple.eventmanager1.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.exammple.eventmanager1.KeyStore;
import com.exammple.eventmanager1.R;

public class ShowLogInPage extends AppCompatActivity {

    EditText editTextUserName, editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextUserName = findViewById(R.id.editTextLogInUserName);
        editTextPassword = findViewById(R.id.editTextLogInPassword);

        // If username has been saved then prefill username value
        SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.LOGIN_DATA_FILE, MODE_PRIVATE);
        String userName = sharedPreferences.getString(KeyStore.USERNAME_KEY, "");

        if(!userName.isEmpty())
        {
            editTextUserName.setText(userName);
        }
    }

    public void onLoginButtonClick(View view)
    {
        // Get edit text values
        String userName = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();

        // Get any saved username and password
        SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.LOGIN_DATA_FILE, MODE_PRIVATE);
        String savedUserName = sharedPreferences.getString(KeyStore.USERNAME_KEY, "default");
        String savedPassword = sharedPreferences.getString(KeyStore.PASSWORD_KEY, "default");

        if(userName.equals(savedUserName) && password.equals(savedPassword))
        {
            Toast.makeText(this, "Successful login", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Username or Password incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    public void onLoginSignUpButtonClick()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}