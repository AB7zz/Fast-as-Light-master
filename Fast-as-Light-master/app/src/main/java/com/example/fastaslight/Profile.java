package com.example.fastaslight;

import static com.example.fastaslight.HomeMenu.user;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class Profile extends AppCompatActivity implements View.OnClickListener{

    /*
        Initializes the visuals of the Profile and sets the touch screen functionality
        for each button within the Profile page.
    */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        TextView e1 = findViewById(R.id.profilename_textView);
        e1.setText(user.getUser_name());

        TextView e2 = findViewById(R.id.profilemail_textView);
        e2.setText(user.getEmail());

        TextView e3 = findViewById(R.id.rank_textview);
        e3.setText("  "+ user.getRank());

        TextView e4 = findViewById(R.id.highscore_textview);
        e4.setText(user.getHighScore());

        TextView e5 = findViewById(R.id.profileID_textview);
        e5.setText("Profile ID:"+"  "+ user.getProfileID());

        String player_char = user.getPlayer_character();
        switch (player_char) {
            case "blue_player":
                ImageView e6 = findViewById(R.id.avatar_img);
                e6.setImageResource(R.drawable.blue_player_1);
                break;
            case "green_player":
                ImageView e7 = findViewById(R.id.avatar_img);
                e7.setImageResource(R.drawable.green_player_1);
                break;
            case "purple_player":
                ImageView e8 = findViewById(R.id.avatar_img);
                e8.setImageResource(R.drawable.purple_player_1);
                break;
            case "red_player":
                ImageView e9 = findViewById(R.id.avatar_img);
                e9.setImageResource(R.drawable.red_player_1);
                break;
            case "yellow_player":
                ImageView e10 = findViewById(R.id.avatar_img);
                e10.setImageResource(R.drawable.yellow_player_1);
                break;
        }

        ImageButton close_profile = findViewById(R.id.close_icon_profile);
        close_profile.setOnClickListener(this);

        ImageButton change_char = findViewById(R.id.change_character_bttn);
        change_char.setOnClickListener(this);
    }

    //    Changes the screen based on the button pressed by the user.
    @Override
    public void onClick(View clicked_button)
    {
        if (clicked_button.getId() == R.id.close_icon_profile)
        {
            finish();
        }
        else if (clicked_button.getId() == R.id.change_character_bttn)
        {
            startActivity(new Intent(this, ChangePlayer.class));
            finish();
        }

    }

}