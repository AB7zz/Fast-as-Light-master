package com.example.fastaslight;

import static com.example.fastaslight.HomeMenu.database;
import static com.example.fastaslight.HomeMenu.key;
import static com.example.fastaslight.HomeMenu.user;
import static com.example.fastaslight.Playthrough.continues;
import static com.example.fastaslight.Playthrough.playthrough;
import static com.example.fastaslight.Playthrough.totalCurrency;
import static com.example.fastaslight.StartPlaythrough.hourText;
import static com.example.fastaslight.StartPlaythrough.minText;
import static com.example.fastaslight.StartPlaythrough.secText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class Continue extends AppCompatActivity implements View.OnClickListener {

    Button continueBtn;
    TextView textScore, textCurrency, highScoreText;
    StringTokenizer st;
    String userHour, userMin, userSec;
    Boolean highScore = false;
    Map<String, Integer> rank = new HashMap<>();
    Map<String, Object> childrenLB, leaderboard;
    LinkedHashMap<String, Integer> sortedRank = new LinkedHashMap<>();
    int continueCost, high_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playthrough_continue);

        ImageButton  exit_continue = findViewById(R.id.close_icon_continue);
        exit_continue.setOnClickListener(this);

        findViewById(R.id.new_game).setOnClickListener(this);
        continueBtn = findViewById(R.id.continue_button);
        highScoreText = findViewById(R.id.high_score_continue);
        highScoreText.setVisibility(View.GONE);

        continueCost = 150 * continues;

        if (continues < 4) {
            continueBtn.setOnClickListener(this);
            continueBtn.setText("Continue " + continueCost);
        } else {
            continueBtn.setVisibility(View.GONE);
        }

        textScore = findViewById(R.id.continue_time);
        textScore.setText(String.format("%s:%s:%s", hourText, minText, secText));

        textCurrency = findViewById(R.id.continue_currency);
        textCurrency.setText("" + totalCurrency);

        st = new StringTokenizer(user.getHighScore(), ":");
        userHour = st.nextToken();
        userMin = st.nextToken();
        userSec = st.nextToken();

        if (Integer.parseInt(hourText) > Integer.parseInt(userHour)) {
            highScoreText.setVisibility(View.VISIBLE);
            highScore = true;
        } else if (Integer.parseInt(minText) > Integer.parseInt(userMin) && Integer.parseInt(hourText) >= Integer.parseInt(userHour)) {
            highScoreText.setVisibility(View.VISIBLE);
            highScore = true;
        } else if (Integer.parseInt(secText) > Integer.parseInt(userSec) && Integer.parseInt(minText) >= Integer.parseInt(userMin) && Integer.parseInt(hourText) >= Integer.parseInt(userHour)) {
            highScoreText.setVisibility(View.VISIBLE);
            highScore = true;
        }



        if (highScore) {
            UpdateDB updateDB = new UpdateDB(
                    user.getProfileID(),
                    user.getEmail(),
                    String.format("%s:%s:%s", hourText, minText, secText),
                    user.getPassword(),
                    user.getPlayer_character(),
                    user.getRank(),
                    user.getUser_name());

            Map<String, Object> updatedValues = updateDB.toMap();

            Map<String, Object> updateChild = new HashMap<>();
            updateChild.put("User/" + key, updatedValues);

            database.updateChildren(updateChild);

            sort();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.close_icon_continue)
        {
            playthrough.finish();
            finish();
        }

        if (view.getId() == R.id.new_game) {
            playthrough.finish();
            finish();
            startActivity(new Intent(this, StartPlaythrough.class));
        }

        if (view.getId() == R.id.continue_button) {
            if (totalCurrency >= continueCost) {
                totalCurrency -= continueCost;
                finish();
            } else {
                errorMessage(this.getWindow().getDecorView().getRootView());
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        playthrough.finish();
        finish();
    }

    public void errorMessage(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.error_popup, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setElevation(20);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public void sort() {
        DatabaseReference users = database.child("User");
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                leaderboard = new HashMap<>();
                for (DataSnapshot userDB : snapshot.getChildren()) {
                    childrenLB = new HashMap<>();
                    String userKey = userDB.getKey();
                    String userHS = userDB.child("highScore").getValue(String.class);
                    st = new StringTokenizer(userHS, ":");
                    high_score = Integer.parseInt(st.nextToken()) * 3600;
                    high_score += Integer.parseInt(st.nextToken()) * 60;
                    high_score += Integer.parseInt(st.nextToken());

                    childrenLB.put("ProfileID", userDB.child("ProfileID").getValue(Integer.class));
                    childrenLB.put("user_name", userDB.child("user_name").getValue(String.class));
                    childrenLB.put("key", userKey);
                    childrenLB.put("highScore", userDB.child("highScore").getValue(String.class));

                    leaderboard.put(userKey, childrenLB);

                    rank.put(userKey, Integer.valueOf(high_score));
                }
                rank.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> sortedRank.put(x.getKey(), x.getValue()));
                Set<String> keys = sortedRank.keySet();
                HashMap<String, Object> newRank = new HashMap<>();
                HashMap<String, Object> newUserRank = new HashMap<>();
                int index = 1, currentUserRank = 0;

                for (String sortedKey : keys) {
                    newRank.put(Integer.toString(index), leaderboard.get(sortedKey));
                    newUserRank.put("User/" + sortedKey + "/rank/", index);
                    if (key.equals(sortedKey)) {
                        currentUserRank = index;
                    }
                    index++;
                }

                Map<String, Object> updateChild = new HashMap<>();
                updateChild.put("Leaderboard/", newRank);

                database.updateChildren(updateChild);
                database.updateChildren(newUserRank);

                user.setHighScore(String.format("%s:%s:%s", hourText, minText, secText));
                user.setRank(currentUserRank);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }
}