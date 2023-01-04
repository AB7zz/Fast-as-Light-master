package com.example.fastaslight;

import static com.example.fastaslight.Playthrough.speedMultiplier;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GrapplerEnemy extends EnemyAI
{
    int speed = 20;
    Player player;

    public GrapplerEnemy(int screenX, Resources res, Player player)
    {
        super(screenX, res);
        this.player = player;

        Bitmap grappler_enemy = BitmapFactory.decodeResource(res, R.drawable.grappler_enemy_1);
        enemy = Bitmap.createScaledBitmap(grappler_enemy, width, height, true);
    }

    @Override
    public void update()
    {
        y += speed * speedMultiplier;

        if(x < player.x && y < player.y + 20)
        {
            x += 5;
        }
        else
        {
            x -= 5;
        }
    }
}
