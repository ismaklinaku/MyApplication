package com.example.myapplication;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {
    EditText emailInput;
    Button resetPasswordButton;

    DB DB;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_forgot_password);

        DB = new DB(this);

        emailInput = findViewById(R.id.Email_input);
        resetPasswordButton = findViewById(R.id.rereset_password);

        resetPasswordButton.setOnClickListener(v-> {
            String email=emailInput.getText().toString().trim();

            if(email.isEmpty()) {
                Toast.makeText(this,"Please enter a valid email address.",Toast.LENGTH_SHORT).show();
                return;
            }
            if(DB.userExists(email)) {
                Intent intent=new Intent(this,Verify.class);
                intent.putExtra("From_forgotPassword",true);
                intent.putExtra("USER_EMAIL",email);
                startActivity(intent);
            }else{
                Toast.makeText(this,"Please check your credentials again!",Toast.LENGTH_SHORT).show();
            }
        });

    }


}
