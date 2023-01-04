package com.example.fastaslight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private String email, password;
    private TextView emailText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        emailText = findViewById(R.id.usernamesignup);
        passwordText = findViewById(R.id.passwordsignup);
        findViewById(R.id.submitsignup).setOnClickListener(this);
        findViewById(R.id.forgotPassword).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submitsignup) {
            email = emailText.getText().toString();
            password = passwordText.getText().toString();

            GetUserDB userDB = new GetUserDB(email, password, this);
        }

        if (view.getId() == R.id.forgotPassword) {
            startActivity(new Intent(this, ForgotPassword.class));
        }
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(this, LoginOrSignUp.class));
        finish();
    }
}
