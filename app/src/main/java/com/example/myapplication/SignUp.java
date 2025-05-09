package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    EditText name;
    EditText username;
    EditText email;
    EditText number;
    EditText password;
    Button signup_button;
    DB DB;
    TextView login_button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name= findViewById(R.id.nameTextField);
        username= findViewById(R.id.usernameTextField);
        email= findViewById(R.id.emailTextField);
        password= findViewById(R.id.passwordTextField);
        number= findViewById(R.id.phoneTextField);

        signup_button = findViewById(R.id.signup_button);
        login_button = findViewById(R.id.login_button);


        DB = new DB(this);
        login_button = findViewById(R.id.login_button);


        login_button.setOnClickListener(view->{
            startActivity(new Intent(this, LogIn.class));
        });
        signup_button.setOnClickListener(view->{

            if (validateFields()) {
                Log.d(TAG, "onCreate: Validation successful");

                String name1 = name.getText().toString().trim();
                String email1 = email.getText().toString().trim();
                String password1 = password.getText().toString().trim();
                String surname = username.getText().toString().trim();
                String phone = number.getText().toString().trim();

                Log.d(TAG, "onCreate: Name: " + name1 + ", Email: " + email1 + ", Surname: " + surname + ", Phone: " + phone);

                // Proceed with signup logic
                if (DB.checkEmail(email1)) {
                    Toast.makeText(this, "User already exists.", Toast.LENGTH_SHORT).show();
                } else if (DB.insertUser(email1,password1,name1,surname,phone)) {
                    Intent intent = new Intent(this, Verify.class );
                    intent.putExtra("USER_EMAIL", email1);
                    intent.putExtra("From_forgotPassword", false);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Failed to register user.", Toast.LENGTH_SHORT).show();
                }
            }




        });

    }
    private boolean validateFields() {
        // Retrieve values
        String name1 = name.getText().toString().trim();
        String surname = username.getText().toString().trim();
        String email2 = email.getText().toString().trim();
        String phone = number.getText().toString().trim();
        String password1 = password.getText().toString().trim();

        // Validate Name (only alphabets, not empty)
        if (TextUtils.isEmpty(name1) || !name1.matches("[a-zA-Z]+")) {
            name.setError("Name must contain only letters and not be empty.");
            return false;
        }

        // Validate Surname (only alphabets, not empty)
        if (TextUtils.isEmpty(surname) || !surname.matches("[a-zA-Z]+")) {
            username.setError("Surname must contain only letters and not be empty.");
            return false;
        }

        // Validate Email (non-empty and valid format)
        if (TextUtils.isEmpty(email2) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email2).matches()) {
            email.setError("Enter a valid email address.");
            return false;
        }

        // Validate Phone (non-empty, valid format)
        if (TextUtils.isEmpty(phone) || !phone.matches("\\d{10,15}")) {
            number.setError("Enter a valid phone number (10-15 digits).");
            return false;
        }

        // Validate Password
        // At least 1 lowercase, 1 uppercase, 1 digit, 1 special character, 6-20 characters, no spaces
        if (TextUtils.isEmpty(password1) ||
                !Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{6,20}$").matcher(password1).matches()) {
            password.setError("Password must contain 1 lowercase, 1 uppercase, 1 digit, 1 special character, and be 6-20 characters long.");
            return false;
        }

        // If all validations pass
        return true;
    }
}
