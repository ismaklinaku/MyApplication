package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity {
    Button signup_button;
    EditText email;
    EditText password;
    TextView forgot_password1;
    Button login_button;
    ImageView image1;
    DB DB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup_button = findViewById(R.id.signup_button);
        email = findViewById(R.id.email);
        password= findViewById(R.id.password);
        forgot_password1 = findViewById(R.id.Forgot_Password);
        login_button = findViewById(R.id.login_button);
        image1 = findViewById(R.id.imageView);

        DB = new DB(this);

        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        image1.startAnimation(pulse);

        login_button.setOnClickListener(view->{
            String email2 = email.getText().toString().trim();
            String password2 = password.getText().toString().trim();

            if (email2.isEmpty() || password2.isEmpty()) {
                Toast.makeText(LogIn.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return; }
            if (!DB.userExists(email2)) {
                Toast.makeText(LogIn.this, "Account does not exist", Toast.LENGTH_SHORT).show();
            } else if (!DB.validateUser(email2, password2)) {
                Toast.makeText(LogIn.this, "Incorrect password", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, HomePage.class);
                intent.putExtra("USER_EMAIL", email2);
                startActivity(intent);



            }

        });

        signup_button.setOnClickListener(view->{
            startActivity(new Intent(this, SignUp.class));
        });
        forgot_password1.setOnClickListener(view->{
            startActivity(new Intent(this, ForgotPassword.class));
        });

    }



}
