package com.example.fastaslight;

import static com.example.fastaslight.Playthrough.screenRatioX;
import static com.example.fastaslight.Playthrough.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Cloud {
    public int speed = 20;
    int x, y, width, height;
    Bitmap cloud;

    Cloud (Resources res, int screenY) {
        cloud = BitmapFactory.decodeResource(res, R.drawable.cloud);

        width = 250 * (int) screenRatioX;
        height = 200 * (int) screenRatioY;

        cloud = Bitmap.createScaledBitmap(cloud, width, height, false);

        y = screenY + height;
    }

    Bitmap getCloud () {
        return cloud;
    }

    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }
}
