package com.example.fastaslight;

import static com.example.fastaslight.HomeMenu.database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SignUp  extends AppCompatActivity implements View.OnClickListener {

    private String username, email, password, confirmpassword, profileidtemp;
    private int profileidfinal;
    private String high_score;
    TextView error;
    String emaildb, usernamedb, passworddb;
    private TextView usernameText, emailText, passwordText, confirmpasswordText;
    ProfileID_Generator profileid = new ProfileID_Generator();
    getMaxID max_id = new getMaxID();
    private int maxID;
    getLastRank rankv = new getLastRank();
    private int rankg;
    DatabaseReference refL = database.child("Leaderboard");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        usernameText = findViewById(R.id.usernamesignup2);
        emailText = findViewById(R.id.emailsignup2);
        passwordText = findViewById(R.id.passwordsignup500);
        confirmpasswordText = findViewById(R.id.passwordsignup501);
        error = findViewById(R.id.errorPassTextsignup500);
        error.setVisibility(View.GONE);
        findViewById(R.id.submitsignup50).setOnClickListener(this);
    }
    public void onClick(View view) {
        if (view.getId() == R.id.submitsignup50) {

            username = usernameText.getText().toString();
            email = emailText.getText().toString();
            password = passwordText.getText().toString();
            confirmpassword = confirmpasswordText.getText().toString();
            if (password.equals(confirmpassword)) {
                DatabaseReference users = database.child("User");

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot userDB : snapshot.getChildren())
                        {
                            emaildb = userDB.child("email").getValue(String.class);
                            usernamedb = userDB.child("user_name").getValue(String.class);
                            passworddb = userDB.child("password").getValue(String.class);
                            System.out.println(emaildb);

                            if (emaildb.isEmpty()&& usernamedb.isEmpty()&&passworddb.isEmpty()) {
                                if(email.equalsIgnoreCase(emaildb) || password.equalsIgnoreCase(passworddb) || username.equalsIgnoreCase(usernamedb))
                                {
                                    System.out.println("You have an account");
//                                   //put system notification
                                    break;
                                }
                            }
                            else
                            {
                                maxID = max_id.getMaxidF();
                                maxID++;
                                rankg = rankv.getlastRankF();
                                rankg++;
                                String keyL = Integer.toString(rankg);
                                String key = Integer.toString(maxID);
                                String default_player = "blue_player";
                                profileidfinal = profileid.getProfileID();
                                high_score = "00:00:00";
                                users.child(key).child("ProfileID").setValue(profileidfinal);
                                users.child(key).child("highScore").setValue(high_score);
                                users.child(key).child("rank").setValue(rankg);
                                users.child(key).child("email").setValue(email);
                                users.child(key).child("password").setValue(password);
                                users.child(key).child("user_name").setValue(username);
                                users.child(key).child("player_character").setValue(default_player);
                                refL.child(keyL).child("ProfileID").setValue(profileidfinal);
                                refL.child(keyL).child("key").setValue(key);
                                refL.child(keyL).child("user_name").setValue(username);
                                refL.child(keyL).child("highScore").setValue(high_score);
                                break;
                            }

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });
            }
            else
            {
                error.setVisibility(View.VISIBLE);
            }
            startActivity(new Intent(this, Login.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoginOrSignUp.class));
        finish();
    }
}



