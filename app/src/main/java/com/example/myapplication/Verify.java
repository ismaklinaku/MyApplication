package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import javax.mail.MessagingException;

public class Verify extends AppCompatActivity {
    EditText otp;
    private Button checkCode;
    private String emailRecipient;
    private TextView resendCode;
    private String code = generateCode();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        otp = findViewById(R.id.code);
        checkCode= findViewById(R.id.checkcode);
        resendCode= findViewById(R.id.resendCode);
        emailRecipient = getIntent().getStringExtra("USER_EMAIL");

        if (emailRecipient == null || emailRecipient.isEmpty()) {
            Toast.makeText(this, "Email address is missing. Please try again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        sendEmail();

        checkCode.setOnClickListener(v -> checkCode());
        resendCode.setOnClickListener(view -> resendCode());
    }

    private void sendEmail() {
        new Thread(() -> {
            Log.d(TAG, "Sending email...");
            try {
                Log.d(TAG, "Sending email to: " + emailRecipient);
                Log.d(TAG, "Sending code: " + code);
                EmailSender.sendCode(emailRecipient, code);
                Log.d(TAG, "Email sent successfully");
                runOnUiThread(() -> Toast.makeText(this, "6 Digit Code sent to " + emailRecipient, Toast.LENGTH_LONG).show());
            } catch (MessagingException e) {
                Log.e(TAG, "Error sending OTP code", e);
                runOnUiThread(() -> Toast.makeText(this, "Failed to send email. Please check your network or credentials.", Toast.LENGTH_LONG).show());
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error in thread", e);
                runOnUiThread(() -> Toast.makeText(this, "An unexpected error occurred.", Toast.LENGTH_LONG).show());
            }
        }).start();
    }

    private void checkCode() {
        Intent intent = getIntent();
        boolean fromForget = intent.getBooleanExtra("From_forgotPassword", false);
        if (!EmailSender.isOtpValid()) {
            Toast.makeText(this, "OTP has expired. Please request a new one.", Toast.LENGTH_SHORT).show();
            return;
        }

        String inputString = otp.getText().toString().trim();
        if (inputString.equals(code)) {
            if(fromForget){
                startActivity(new Intent(this, ResetPassword.class));
            }else {
                Toast.makeText(this, "Account verified, you may proceed to log in!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LogIn.class));
            }
        } else {
            Toast.makeText(this, "Incorrect code, try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void resendCode() {
        code = generateCode();
        sendEmail();
    }

    private String generateCode() {
        Random random = new Random();
        return String.valueOf(100000 + random.nextInt(900000));


    }

}
