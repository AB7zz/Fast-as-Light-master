package com.example.fastaslight;

import static com.example.fastaslight.HomeMenu.environments;

import android.content.res.Resources;
import android.graphics.Bitmap;

public class PlaythroughBackground {

    int x, y, background_index;
    Bitmap background;

    public PlaythroughBackground (int start_environment, int screenX, int screenY, Resources res)
    {
        background_index = start_environment;
        background = environments.get(background_index);
    }

    public void switchBackground()
    {
        if (background_index < 5)
        {
            background_index += 1;
        }
        else
        {
            background_index = 0;
        }

        background = environments.get(background_index);
    }
}
