package com.example.fastaslight;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class UpdateDB {
    private String user_name;
    private String email;
    private String password;
    private String highScore;
    private int rank ;
    private String player_character;
    private int ProfileID;

    public UpdateDB() {

    }

    public UpdateDB(int ProfileID, String email, String highScore, String password, String player_character, int rank, String user_name) {
        this.ProfileID = ProfileID;
        this.email = email;
        this.highScore = highScore;
        this.password = password;
        this.player_character = player_character;
        this.rank = rank;
        this.user_name = user_name;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ProfileID", ProfileID);
        result.put("email", email);
        result.put("highScore", highScore);
        result.put("password", password);
        result.put("player_character", player_character);
        result.put("rank", rank);
        result.put("user_name", user_name);

        return result;
    }
}
