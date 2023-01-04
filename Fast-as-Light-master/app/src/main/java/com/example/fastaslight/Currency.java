package com.example.fastaslight;

import static com.example.fastaslight.Playthrough.screenRatioX;
import static com.example.fastaslight.Playthrough.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Currency {
    public int value, speed = 30;
    public int x, y, width, height, xPos, counter = 0;
    private Random random;
    private Boolean side1 = true;
    Bitmap currency1, currency2;

    public Currency(Resources res, int screenX) {
        random = new Random();
        value = random.nextInt(50) + 1;

        currency1 = BitmapFactory.decodeResource(res, R.drawable.currency1);
        currency2 = BitmapFactory.decodeResource(res, R.drawable.currency2);

        width = currency1.getWidth();
        height = currency1.getHeight();

        width *= 1 * (int) screenRatioX;
        height *= 1 * (int) screenRatioY;

        xPos = random.nextInt((int) (screenX * screenRatioX));

        y = -height;
        if (xPos > screenX-width) {
            x = screenX - width;
        } else {
            x = xPos;
        }
    }

    Bitmap getCoin() {
        if (counter < 5 && side1) {
            counter++;
            return currency1;
        } else {
            side1 = false;
        }

        counter--;
        if (counter == 0) {
            side1 = true;
        }
        return currency2;
    }

    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }
}
