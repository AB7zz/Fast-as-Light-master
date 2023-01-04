package com.example.fastaslight;

import static com.example.fastaslight.Playthrough.cardHeight;
import static com.example.fastaslight.Playthrough.screenRatioX;
import static com.example.fastaslight.Playthrough.screenRatioY;
import static com.example.fastaslight.Playthrough.speedMultiplier;
import static com.example.fastaslight.StartPlaythrough.navigationBarHeight;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Plane {
    public int speed = 30;
    public int x, y, width, height, yPos, counter = 0;
    private Random random;
    private Boolean side1 = true;
    public Boolean flipped;
    Bitmap plane1, plane2, plane3, plane4;

    public Plane(Resources res, int screenX, int screenY, Boolean flipped) {
        this.flipped = flipped;
        random = new Random();

        plane1 = BitmapFactory.decodeResource(res, R.drawable.plane_1);
        plane2 = BitmapFactory.decodeResource(res, R.drawable.plane_2);
        plane3 = BitmapFactory.decodeResource(res, R.drawable.plane_3);
        plane4 = BitmapFactory.decodeResource(res, R.drawable.plane_4);

        width = plane1.getWidth();
        height = plane1.getHeight();

        width *= .25 * (int) screenRatioX;
        height *= .25 * (int) screenRatioY;

        plane1 = Bitmap.createScaledBitmap(plane1, width, height, false);
        plane2 = Bitmap.createScaledBitmap(plane2, width, height, false);
        plane3 = Bitmap.createScaledBitmap(plane3, width, height, false);
        plane4 = Bitmap.createScaledBitmap(plane4, width, height, false);


        yPos = random.nextInt(screenY);

        if (!flipped) {
            x = screenX;
        } else {
            x = -width;
        }

        if (yPos < cardHeight) {
            y = cardHeight;
        } else if(yPos > screenY - height - (2 * navigationBarHeight)) {
            y = screenY - height - (2 * navigationBarHeight);
        } else {
            y = yPos;
        }
    }

    Bitmap getPlane() {
        if (!flipped) {
            if (counter < 5 && side1) {
                counter++;
                return plane1;
            } else {
                side1 = false;
            }

            counter--;
            if (counter == 0) {
                side1 = true;
            }
            return plane2;
        } else {
            if (counter < 5 && side1) {
                counter++;
                return plane3;
            } else {
                side1 = false;
            }

            counter--;
            if (counter == 0) {
                side1 = true;
            }
            return plane4;
        }
    }

    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }

    public void updatePlane() {
        if (!flipped) {
            x -= speed * speedMultiplier;
        } else {
            x += speed * speedMultiplier;
        }
    }
}
