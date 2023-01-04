package com.example.fastaslight;

import static com.example.fastaslight.HomeMenu.user;
import static com.example.fastaslight.Playthrough.screenRatioX;
import static com.example.fastaslight.Playthrough.screenRatioY;
import static com.example.fastaslight.StartPlaythrough.navigationBarHeight;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.HashMap;

public class Player {

    public boolean moveLeft = false, moveRight = false, moveUp = false, moveDown = false, shooting = false;
    public int toShoot = 0;
    private boolean jetpack_fire = true;
    int x, y, width, height, counter = 0, health = 3;
    Bitmap player_1, player_2, player_dead;
    private Playthrough playthrough;

    public Player(Playthrough playthrough, int screenX, int screenY, Resources res) {
        this.playthrough = playthrough;

        // Contains all the possible player sprites
        HashMap<String, Integer> player_models = new HashMap<>();

        player_models.put("blue_player2", R.drawable.blue_player_2);
        player_models.put("blue_player3", R.drawable.blue_player_3);
        player_models.put("blue_player4", R.drawable.blue_player_4);

        player_models.put("red_player2", R.drawable.red_player_2);
        player_models.put("red_player3", R.drawable.red_player_3);
        player_models.put("red_player4", R.drawable.red_player_4);

        player_models.put("green_player2", R.drawable.green_player_2);
        player_models.put("green_player3", R.drawable.green_player_3);
        player_models.put("green_player4", R.drawable.green_player_4);

        player_models.put("yellow_player2", R.drawable.yellow_player_2);
        player_models.put("yellow_player3", R.drawable.yellow_player_3);
        player_models.put("yellow_player4", R.drawable.yellow_player_4);

        player_models.put("purple_player2", R.drawable.purple_player_2);
        player_models.put("purple_player3", R.drawable.purple_player_3);
        player_models.put("purple_player4", R.drawable.purple_player_4);

        //  Concatenate the name of player character to a number to find corresponding Bitmap from Hash Table
        player_1 = BitmapFactory.decodeResource(res, player_models.get(user.getPlayer_character().concat("2")));
        player_2 = BitmapFactory.decodeResource(res, player_models.get(user.getPlayer_character().concat("3")));
        player_dead = BitmapFactory.decodeResource(res, player_models.get(user.getPlayer_character().concat("4")));

        width = (int) (player_1.getWidth() * .5 *  screenRatioX);
        height = (int) (player_1.getHeight() * .5 *  screenRatioY);

        player_1 = Bitmap.createScaledBitmap(player_1, width, height, true);
        player_2 = Bitmap.createScaledBitmap(player_2, width, height, true);
        player_dead = Bitmap.createScaledBitmap(player_dead, width, height, true);

        y = screenY - height - (navigationBarHeight * 2) - (int) (20 * screenRatioY);

        x = (screenX / 2) - (width / 2);
    }

    Bitmap getPlayer() {
        if (shooting)
        {
            playthrough.newBullet(x, y, width, true);
        }

        // Animate the player's jetpack every 5 frames
        if (counter < 5 && jetpack_fire)
        {
            counter++;
            return player_1;
        }
        else
        {
            jetpack_fire = false;
        }

        counter--;
        if (counter == 0)
        {
            jetpack_fire = true;
        }
        return player_2;
    }

    Rect getCollisionShape()
    {
        return new Rect(x, y, x + width, y + height);
    }

    Bitmap getDead ()
    {
        return player_dead;
    }
}
