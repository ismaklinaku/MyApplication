package com.example.myapplication;

import android.util.Log;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private static final String adminEmail = "ismaklinaku27@gmail.com";
    private static final String AppPassword = "pxgx rvoo mesj quwi";
    private static long otpGenerationTime;

    public static void sendCode(String receiverEmail, String sixDigitCode) throws MessagingException {
        Log.d("EmailSender", "Preparing to send email to: " + receiverEmail);

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Log.d("EmailSender", "Properties set. Initializing session...");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(adminEmail, AppPassword);
            }
        });

        Log.d("EmailSender", "Session initialized. Preparing message...");
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(adminEmail));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
        message.setSubject("OTP Code");
        message.setText("This is your OTP Code: " + sixDigitCode);
        try {
            Transport.send(message);
            Log.d("EmailSender", "Email sent successfully to: " + receiverEmail);
        } catch (MessagingException e) {
            Log.e("EmailSender", "MessagingException: " + e.getMessage(), e);
            throw e; // Or handle the exception appropriately
        } catch (Exception e) {
            Log.e("EmailSender", "Unexpected error: " + e.getMessage(), e);
            throw e; // Or handle the exception appropriately
        }

        Log.d("EmailSender", "Message prepared. Sending...");
        Transport.send(message);
        Log.d("EmailSender", "Email sent successfully to: " + receiverEmail);

        otpGenerationTime = System.currentTimeMillis();
    }

    public static boolean isOtpValid() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - otpGenerationTime;

        return TimeUnit.MILLISECONDS.toMinutes(elapsedTime) < 2;
    }
}