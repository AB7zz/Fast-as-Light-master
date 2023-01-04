package com.example.fastaslight;

import static com.example.fastaslight.HomeMenu.loggedIn;
import static com.example.fastaslight.HomeMenu.user;
import static com.example.fastaslight.HomeMenu.key;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetUserDB {
    private String email, password;
    private Boolean emailExists = false;
    DatabaseReference users;

    public GetUserDB(String inputEmail, String inputPassword, Login loginView) {
        users = FirebaseDatabase.getInstance().getReference("User");

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userDB : snapshot.getChildren()) {
                    email = userDB.child("email").getValue(String.class);
                    password = userDB.child("password").getValue(String.class);

                    if (inputEmail.equalsIgnoreCase(email)) {
                        emailExists = true;
                    }

                    if (inputEmail.equalsIgnoreCase(email) && inputPassword.equals(password)) {
                        key = userDB.getKey();
                        user = userDB.getValue(User.class);
                        loggedIn = true;
                        loginView.startActivity(new Intent(loginView, HomeMenu.class));
                        loginView.finish();
                    }
                }

                if (emailExists && !loggedIn) {
                    Toast.makeText(loginView, "Incorrect Password", Toast.LENGTH_LONG).show();
                } else if (!loggedIn){
                    Toast.makeText(loginView, "Email does not exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }
}
