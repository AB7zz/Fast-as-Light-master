package com.example.fastaslight;

import static com.example.fastaslight.Playthrough.screenRatioX;
import static com.example.fastaslight.Playthrough.screenRatioY;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import android.content.res.Resources;

import java.util.Random;

public class MedKit
{
    int speed = 30;
    int x, y, width, height, xPos;
    Random random;
    Bitmap med_kit;

    MedKit (Resources res, int screenX)
    {
        random = new Random();

        med_kit = BitmapFactory.decodeResource(res, R.drawable.med_kit);

        width = 150 * (int) screenRatioX;
        height = 100 * (int) screenRatioY;

        med_kit = Bitmap.createScaledBitmap(med_kit, width, height, true);

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

    Bitmap getMedKit()
    {
        return med_kit;
    }

    Rect getCollisionShape()
    {
        return new Rect(x, y, x + width, y + height);
    }
}
