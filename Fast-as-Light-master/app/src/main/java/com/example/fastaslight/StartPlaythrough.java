package com.example.fastaslight;

import static com.example.fastaslight.Playthrough.shotCost;
import static com.example.fastaslight.Playthrough.speedMultiplier;
import static com.example.fastaslight.Playthrough.totalCurrency;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class StartPlaythrough extends AppCompatActivity implements View.OnTouchListener {

    private Playthrough playthrough;
    private int slowCost, totalSlows;
    public static int navigationBarHeight;
    public TextView currency_text, timeScore, slowText, shotCostText;
    public ImageView healthState;
    public static String hourText, minText, secText ;
    public CardView gameUiCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        Point point = new Point();
        getDisplay().getRealSize(point);

        FrameLayout screen = new FrameLayout(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        ConstraintLayout ui = (ConstraintLayout) inflater.inflate(R.layout.start_playthrough,null);

        playthrough = new Playthrough(this, point.x, point.y);

        screen.addView(playthrough);
        screen.addView(ui);

        setContentView(screen);

        findViewById(R.id.right_btn).setOnTouchListener(this);
        findViewById(R.id.left_btn).setOnTouchListener(this);
        findViewById(R.id.up_btn).setOnTouchListener(this);
        findViewById(R.id.down_btn).setOnTouchListener(this);
        findViewById(R.id.shoot_btn).setOnTouchListener(this);
        findViewById(R.id.slow_btn).setOnTouchListener(this);

        currency_text = findViewById(R.id.currency);
        shotCostText = findViewById(R.id.shotCostText);
        slowText = findViewById(R.id.slow_cost);
        healthState = findViewById(R.id.health);
        timeScore = findViewById(R.id.time_score);
        gameUiCard = findViewById(R.id.game_ui_card);

        hourText = "00";
        minText = "00";
        secText = "00";
        slowCost = 50;
        totalSlows = 0;

        slowText.setText(String.valueOf(slowCost));
    }

    @Override
    protected void onPause() {
        super.onPause();
        playthrough.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playthrough.resume();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            switch (view.getId()) {
                case R.id.right_btn:
                    playthrough.getPlayer().moveRight = true;
                    break;
                case  R.id.left_btn:
                    playthrough.getPlayer().moveLeft = true;
                    break;
                case  R.id.up_btn:
                    playthrough.getPlayer().moveUp = true;
                    break;
                case R.id.down_btn:
                    playthrough.getPlayer().moveDown = true;
                    break;
                case R.id.shoot_btn:
                    playthrough.getPlayer().shooting = true;
                    break;
                case R.id.slow_btn:
                    if (totalCurrency >= slowCost) {
                        totalSlows++;
                        totalCurrency -= slowCost;
                        slowCost += 75 * totalSlows;
                        slowText.setText(String.valueOf(slowCost));
                        speedMultiplier = 1;
                    }
            }
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            playthrough.getPlayer().moveRight = false;
            playthrough.getPlayer().moveLeft = false;
            playthrough.getPlayer().moveUp = false;
            playthrough.getPlayer().moveDown = false;
            playthrough.getPlayer().shooting = false;
        }
        return true;
    }

    public void updateCurrencyText() {
        currency_text.setText("" + totalCurrency);
    }

    public void updateBulletCostText() {
        shotCostText.setText("" + shotCost);
    }

    public void  updateHealth(int resId) {
        healthState.setImageResource(resId);
    }

    public void updateTime(int seconds) {
        if (seconds > 59) {
            int minutes = Math.floorDiv(seconds, 60);
            if (seconds % 60 < 10) {
                secText = "0" + (seconds % 60);
            } else {
                secText = "" + (seconds % 60);
            }

            if (minutes > 59) {
                int hours = Math.floorDiv(minutes, 60);
                if (minutes % 60 < 10) {
                    minText = "0" + (minutes % 60);
                } else {
                    minText = "" + (minutes % 60);
                }

                hourText = "0" + hours; //not accounting for anything over 9 hours

                timeScore.setText(String.format("%s:%s:%s", hourText, minText, secText));
                return;
            } else if (minutes < 10) {
                minText = "0" + minutes;
            } else {
                minText = "" + (seconds % 60);
            }
            timeScore.setText(String.format("%s:%s:%s", hourText, minText, secText));
            return;
        } else if (seconds < 10) {
            secText = "0" + seconds;
        } else {
            secText = "" + (seconds % 60);
        }
        timeScore.setText(String.format("%s:%s:%s", hourText, minText, secText));
        return;
    }
}