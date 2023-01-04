package com.example.fastaslight;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class LoginOrSignUp extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_or_signup);

        ImageButton gotol = findViewById(R.id.goto_login);
        gotol.setOnClickListener(this);

        ImageButton gotos = findViewById(R.id.goto_signup);
        gotos.setOnClickListener(this);

        findViewById(R.id.exitBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View clicked_button)
    {
        if (clicked_button.getId() == R.id.goto_login)
        {
            startActivity(new Intent(this, Login.class));
            finish();
        }
        else if (clicked_button.getId() == R.id.goto_signup)
        {
            startActivity(new Intent(this, SignUp.class));
            finish();
        } else if (clicked_button.getId() == R.id.exitBtn) {
            finish();
        }
    }

}
