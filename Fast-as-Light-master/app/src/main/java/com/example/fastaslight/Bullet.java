package com.example.fastaslight;

import static com.example.fastaslight.Playthrough.screenRatioX;
import static com.example.fastaslight.Playthrough.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Bullet {
    int x, y, width, height;
    Boolean shot = false, shotByPlayer;
    Bitmap bullet;

    Bullet (int x, int y, int shooter_width, Boolean shotByPlayer, Resources res)
    {
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);

        width = (int) (bullet.getWidth() * 2 * screenRatioX);
        height = (int) (bullet.getHeight() * 2 * screenRatioY);

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);

        this.shotByPlayer = shotByPlayer;
        this.x = x + (shooter_width / 2) - (width / 2);

        if (shotByPlayer)
        {
            this.y = y;
        }
        else
        {
            this.y = y + 10;
            shot = true;
        }
    }

    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }
}
