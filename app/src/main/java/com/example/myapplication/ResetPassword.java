package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class ResetPassword extends AppCompatActivity {
    EditText resetPass1, resetPass2;
    Button changePassword;
    String email;
    DB DB;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        resetPass1 = findViewById(R.id.resetPassword1);
        resetPass2 = findViewById(R.id.resetPassword2);
        changePassword = findViewById(R.id.changePassword);
        email = getIntent().getStringExtra("Email");

        DB = new DB(this);




        changePassword.setOnClickListener(view->{
            String reset1 = resetPass1.getText().toString().trim();
            String reset2 = resetPass2.getText().toString().trim();
            if(Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{6,20}$").matcher(reset1).matches())
            {
                if(reset1.equals(reset2)){
                    DB.updateUserPassword(email, reset1);
                    Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LogIn.class));
                }else{
                    Toast.makeText(this, "Password don't match", Toast.LENGTH_SHORT).show();
                }
            }else {
                resetPass1.setError("Password must contain 1 lowercase, 1 uppercase, 1 digit, 1 special character, and be 6-20 characters long.");

            }

        });




    }
}

