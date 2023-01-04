package com.example.fastaslight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgotPassword extends AppCompatActivity {
    String resetCode, stringReceiverEmail;
    EditText email, passcode;
    TextView emailText, passcodeText;
    Button changePasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        email = findViewById(R.id.emailForgotPassword);
        passcode = findViewById(R.id.enterPasscode);
        emailText = findViewById(R.id.forgotEmailText);
        passcodeText = findViewById(R.id.enterPasscodeText);
        changePasswordBtn = findViewById(R.id.sendEmail);

        passcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String  userPasscode = charSequence.toString();
                if (userPasscode.equals(resetCode)){
                    passcodeMatches();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passcode.setVisibility(View.GONE);
        passcodeText.setVisibility(View.GONE);
    }

    public void buttonSendEmail(View view){

        Random random = new Random();
        String num1 = String.valueOf(random.nextInt(9));
        String num2 = String.valueOf(random.nextInt(9));
        String num3 = String.valueOf(random.nextInt(9));
        String num4 = String.valueOf(random.nextInt(9));

        resetCode = num1 + num2 + num3 + num4;
        stringReceiverEmail = email.getText().toString();

        try {
            String stringSenderEmail = "fastaslight3@gmail.com";
            String stringPasswordSenderEmail = "ejgmwjmyhhgxskrh";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Subject: Change Password");
            mimeMessage.setText(String.format("Hello %s, \n\nThe code to change your password is %s.\n\nThank you!\nFastasLight Team", stringReceiverEmail, resetCode));

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        email.setVisibility(View.GONE);
        emailText.setVisibility(View.GONE);
        changePasswordBtn.setVisibility(View.GONE);

        passcode.setVisibility(View.VISIBLE);
        passcodeText.setVisibility(View.VISIBLE);
    }

    public void passcodeMatches () {
        startActivity(new Intent(this, ChangePassword.class).putExtra("email", stringReceiverEmail));
        finish();
    }
}