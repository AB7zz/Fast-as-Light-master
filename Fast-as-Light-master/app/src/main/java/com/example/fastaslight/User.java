package com.example.fastaslight;

public class User {
    private String user_name;
    private String email;
    private String password;
    private String highScore;
    private int rank ;
    private String player_character;
    private int ProfileID;

    User() {}

    public User(int ProfileID, String email, String highScore, String password, String player_character, int rank, String user_name) {
        this.ProfileID = ProfileID;
        this.email = email;
        this.highScore = highScore;
        this.password = password;
        this.player_character = player_character;
        this.rank = rank;
        this.user_name = user_name;
    }

    public int getProfileID() { return ProfileID; }

    public String getEmail() {
        return email;
    }

    public String getHighScore() {
        return highScore;
    }
    public void setHighScore(String highScore) { this.highScore = highScore; }

    public String getPassword() {
        return password;
    }

    public String getPlayer_character() {
        return player_character;
    }

    public int getRank() { return rank; }
    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setPlayer_character(String player_character) {
        this.player_character = player_character;
    }
    public void setUser_name(String user_name){
        this.user_name = user_name;
    }
}
