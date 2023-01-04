package com.example.fastaslight;

import static com.example.fastaslight.Playthrough.speedMultiplier;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BasicEnemy extends EnemyAI
{
    int speed = 30;

    public BasicEnemy(int screenX, Resources res)
    {
        super(screenX, res);
        Bitmap basic_enemy = BitmapFactory.decodeResource(res, R.drawable.basic_enemy_1);
        enemy = Bitmap.createScaledBitmap(basic_enemy, width, height, true);
    }

    @Override
    public void update()
    {
        y += speed * speedMultiplier;
    }
}
