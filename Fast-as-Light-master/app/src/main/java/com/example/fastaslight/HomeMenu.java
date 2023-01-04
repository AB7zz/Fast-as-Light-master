package com.example.fastaslight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HomeMenu extends AppCompatActivity implements View.OnClickListener
{
    public static Boolean loggedIn = false;
    public static User user;
    public static String key;
    public static DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    public static List<Bitmap> environments = new ArrayList<>();

    /*
        Initializes the visuals of the Home Menu and sets the touch screen functionality
        for each button within the Home Menu.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_menu);

        Point point = new Point();
        getDisplay().getRealSize(point);

        Resources res = getResources();
        int screenX = point.x;
        int screenY = 2 * point.y;

        Bitmap morning = BitmapFactory.decodeResource(res, R.drawable.morning);
        Bitmap evening_transition = BitmapFactory.decodeResource(res, R.drawable.evening_transition);
        Bitmap evening = BitmapFactory.decodeResource(res, R.drawable.evening);
        Bitmap night_transition = BitmapFactory.decodeResource(res, R.drawable.night_transition);
        Bitmap night = BitmapFactory.decodeResource(res, R.drawable.night);
        Bitmap morning_transition = BitmapFactory.decodeResource(res, R.drawable.morning_transition);

        environments.add(Bitmap.createScaledBitmap(morning, screenX, screenY, true));
        environments.add(Bitmap.createScaledBitmap(evening_transition, screenX, screenY, true));
        environments.add(Bitmap.createScaledBitmap(evening, screenX, screenY, true));
        environments.add(Bitmap.createScaledBitmap(night_transition, screenX, screenY, true));
        environments.add(Bitmap.createScaledBitmap(night, screenX, screenY, true));
        environments.add(Bitmap.createScaledBitmap(morning_transition, screenX, screenY, true));


        ImageButton view_profile = findViewById(R.id.profile_button);
        view_profile.setOnClickListener(this);

        ImageButton start_game = findViewById(R.id.start_button);
        start_game.setOnClickListener(this);

        ImageButton open_leaderboard = findViewById(R.id.leaderboard_button);
        open_leaderboard.setOnClickListener(this);

        ImageButton open_settings = findViewById(R.id.settings_button);
        open_settings.setOnClickListener(this);

        if (!loggedIn) {
            startActivity(new Intent(this, LoginOrSignUp.class));
            finish();
        }
    }

    //    Changes the screen based on the button pressed by the user.
    @Override
    public void onClick(View clicked_button)
    {
        if (clicked_button.getId() == R.id.profile_button)
        {
            startActivity(new Intent(this, Profile.class));
        }
        else if (clicked_button.getId() == R.id.start_button)
        {
            startActivity(new Intent(this, StartPlaythrough.class));
        }
        else if (clicked_button.getId() == R.id.leaderboard_button)
        {
            startActivity(new Intent(this, Leaderboard.class));
        }
        else if (clicked_button.getId() == R.id.settings_button)
        {
            startActivity(new Intent(this, Settings.class));
        }
    }
}