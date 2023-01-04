package com.example.fastaslight;

import static com.example.fastaslight.HomeMenu.database;
import static com.example.fastaslight.HomeMenu.key;
import static com.example.fastaslight.HomeMenu.user;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class ChangePlayer extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_player);

        findViewById(R.id.close_icon_change_char).setOnClickListener(this);
        findViewById(R.id.blue_player_1_change_player).setOnClickListener(this);
        findViewById(R.id.green_player_1_change_player).setOnClickListener(this);
        findViewById(R.id.purple_player_1_change_player).setOnClickListener(this);
        findViewById(R.id.red_player_1_change_player).setOnClickListener(this);
        findViewById(R.id.yellow_player_1_change_player).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.close_icon_change_char) {
            finish();
        }
        else if (view.getId() == R.id.blue_player_1_change_player){
            user.setPlayer_character("blue_player");
            UpdateDB updateDB = new UpdateDB(
                    user.getProfileID(),
                    user.getEmail(),
                    user.getHighScore(),
                    user.getPassword(),
                    user.getPlayer_character(),
                    user.getRank(),
                    user.getUser_name());

            Map<String, Object> updatedValues = updateDB.toMap();

            Map<String, Object> updateChild = new HashMap<>();
            updateChild.put("User/" + key, updatedValues);

            database.updateChildren(updateChild);
            startActivity(new Intent(this, Profile.class));
            finish();
        }
        else if(view.getId() == R.id.green_player_1_change_player){
            user.setPlayer_character("green_player");
            UpdateDB updateDB = new UpdateDB(
                    user.getProfileID(),
                    user.getEmail(),
                    user.getHighScore(),
                    user.getPassword(),
                    user.getPlayer_character(),
                    user.getRank(),
                    user.getUser_name());

            Map<String, Object> updatedValues = updateDB.toMap();

            Map<String, Object> updateChild = new HashMap<>();
            updateChild.put("User/" + key, updatedValues);

            database.updateChildren(updateChild);
            startActivity(new Intent(this, Profile.class));
            finish();
        }
        else if(view.getId() == R.id.purple_player_1_change_player){
            user.setPlayer_character("purple_player");
            UpdateDB updateDB = new UpdateDB(
                    user.getProfileID(),
                    user.getEmail(),
                    user.getHighScore(),
                    user.getPassword(),
                    user.getPlayer_character(),
                    user.getRank(),
                    user.getUser_name());

            Map<String, Object> updatedValues = updateDB.toMap();

            Map<String, Object> updateChild = new HashMap<>();
            updateChild.put("User/" + key, updatedValues);

            database.updateChildren(updateChild);
            startActivity(new Intent(this, Profile.class));
            finish();
        }
        else if(view.getId() == R.id.red_player_1_change_player){
            user.setPlayer_character("red_player");
            UpdateDB updateDB = new UpdateDB(
                    user.getProfileID(),
                    user.getEmail(),
                    user.getHighScore(),
                    user.getPassword(),
                    user.getPlayer_character(),
                    user.getRank(),
                    user.getUser_name());

            Map<String, Object> updatedValues = updateDB.toMap();

            Map<String, Object> updateChild = new HashMap<>();
            updateChild.put("User/" + key, updatedValues);

            database.updateChildren(updateChild);
            startActivity(new Intent(this, Profile.class));
            finish();
        }
        else if(view.getId() == R.id.yellow_player_1_change_player){
            user.setPlayer_character("yellow_player");
            UpdateDB updateDB = new UpdateDB(
                    user.getProfileID(),
                    user.getEmail(),
                    user.getHighScore(),
                    user.getPassword(),
                    user.getPlayer_character(),
                    user.getRank(),
                    user.getUser_name());

            Map<String, Object> updatedValues = updateDB.toMap();

            Map<String, Object> updateChild = new HashMap<>();
            updateChild.put("User/" + key, updatedValues);

            database.updateChildren(updateChild);
            startActivity(new Intent(this, Profile.class));
            finish();
        }
    }
}

