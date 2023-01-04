package com.example.fastaslight;

import static com.example.fastaslight.HomeMenu.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {
    EditText newPassword, confirmPassword;
    TextView error;
    String inputEmail, email, userKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        inputEmail = getIntent().getExtras().getString("email");
        System.out.println(inputEmail);

        newPassword = findViewById(R.id.newPassword_forgot);
        confirmPassword = findViewById(R.id.confirmPassword_forgot);
        error = findViewById(R.id.errorPassText);

        error.setVisibility(View.GONE);
    }

    public void changePassword(View view) {
        String newPass = newPassword.getText().toString();
        String confirmPass = confirmPassword.getText().toString();

        if (newPass.equals(confirmPass)) {
            DatabaseReference users = database.child("User");

            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot userDB : snapshot.getChildren()) {
                        email = userDB.child("email").getValue(String.class);
                        System.out.println(email);

                        if (inputEmail.equalsIgnoreCase(email)) {
                            userKey = userDB.getKey();
                            System.out.println(userKey);
                            Map<String, Object> updatePass = new HashMap<>();
                            updatePass.put("User/" + userKey + "/password/" , newPass);
                            database.updateChildren(updatePass);
                            passwordChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    throw error.toException();
                }
            });
        } else {
            error.setVisibility(View.VISIBLE);
        }
    }

    public void passwordChanged() {
        finish();
    }
}