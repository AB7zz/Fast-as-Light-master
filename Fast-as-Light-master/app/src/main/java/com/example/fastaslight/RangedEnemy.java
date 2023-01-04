package com.example.fastaslight;

import static com.example.fastaslight.Playthrough.speedMultiplier;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.Random;

public class RangedEnemy extends EnemyAI
{
    int speed = 10, counter = 0;
    Playthrough playthrough;
    Boolean jetpack_fire = true;
    Random random;
    Bitmap ranged_enemy_1, ranged_enemy_2;

    public RangedEnemy(int screenX, Resources res, Playthrough playthrough)
    {
        super(screenX, res);
        this.playthrough = playthrough;

        Bitmap ranged_1 = BitmapFactory.decodeResource(res, R.drawable.ranged_enemy_1);
        Bitmap ranged_2 = BitmapFactory.decodeResource(res, R.drawable.ranged_enemy_2);

        ranged_enemy_1 = Bitmap.createScaledBitmap(ranged_1, width, height, true);
        ranged_enemy_2 = Bitmap.createScaledBitmap(ranged_2, width, height, true);

        random = new Random();
    }

    @Override
    public Bitmap getEnemy()
    {
        if (counter < 5 && jetpack_fire)
        {
            counter++;
            return ranged_enemy_1;
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
        return ranged_enemy_2;
    }

    @Override
    public void update()
    {
        if (y < height * 1.5)
        {
            y += speed * speedMultiplier;
        }
        else
        {
            if (random.nextInt(100) > 96)
            {
                playthrough.newBullet(x, y + height, width, false);
            }
        }

    }


}
