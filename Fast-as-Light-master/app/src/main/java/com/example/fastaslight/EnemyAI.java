package com.example.fastaslight;

import static com.example.fastaslight.Playthrough.screenRatioX;
import static com.example.fastaslight.Playthrough.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class EnemyAI
{
    int speed, x, y, width, height, xPos;
    Random random;
    Bitmap enemy;

    public EnemyAI()
    {

    }

    public EnemyAI(int screenX, Resources res)
    {
        random = new Random();

        enemy = BitmapFactory.decodeResource(res, R.drawable.basic_enemy_1);

        width = (int) (enemy.getWidth() * 0.6 * screenRatioX);
        height = (int) (enemy.getHeight() * 0.6 * screenRatioY);

        xPos = random.nextInt((int) (screenX * screenRatioX));

        y = -height;

        if (xPos > screenX - width)
        {
            x = screenX - width;
        }
        else
        {
            x = xPos;
        }
    }

    public void update() { }

    Bitmap getEnemy() { return enemy; }

    Rect getCollisionShape() { return new Rect(x, y, x + width, y + height); }
}
