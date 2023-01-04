package com.example.fastaslight;

import static com.example.fastaslight.HomeMenu.key;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Leaderboard extends AppCompatActivity implements View.OnClickListener{

    private HashMap<Integer, Object> mainleaderboard = new HashMap<>();
    private ArrayList<TextView> textviews = new ArrayList<>();
    private LinearLayout linearLayout;
    LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        ImageButton close_profile = findViewById(R.id.close_icon_leaderboard);
        close_profile.setOnClickListener(this);

        linearLayout = findViewById(R.id.LinearLayout2);

        DatabaseReference refL = FirebaseDatabase.getInstance().getReference("Leaderboard");

        refL.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userDB : snapshot.getChildren()) {
                    HashMap<String, Object> leaderboard_data = new HashMap<>();
                    leaderboard_data.put("ProfileID",userDB.child("ProfileID").getValue(Integer.class));
                    leaderboard_data.put("user_name",userDB.child("user_name").getValue(String.class));
                    leaderboard_data.put("highScore", userDB.child("highScore").getValue(String.class));
                    leaderboard_data.put("key", userDB.child("key").getValue(String.class));

                    mainleaderboard.put(Integer.parseInt(userDB.getKey()),leaderboard_data);
                }
                displayLeaderboard();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    @Override
    public void onClick(View clicked_button)
    {
        if (clicked_button.getId() == R.id.close_icon_leaderboard)
        {
            startActivity(new Intent(this, HomeMenu.class));
        }
    }

    public void displayLeaderboard(){
        Set<Integer> keys = mainleaderboard.keySet();
        int count=0;
        for(Integer keyLB: keys){
            textviews.add(new TextView(this));
            TextView textView2 = textviews.get(count);
            if (keyLB == 1) {
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,300,0,100);
                textView2.setLayoutParams(params);
            } else {
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,0,0,100);
                textView2.setLayoutParams(params);
            }
            textView2.setGravity(Gravity.CENTER);
            Map<String, Object> rank = (HashMap<String, Object>) mainleaderboard.get(keyLB);
            String string2 = "#RANK: "+ keyLB +"    " + rank.get("user_name") + "\nID: " + rank.get("ProfileID")+"\n    High Score: " + rank.get("highScore");
            textView2.setText(string2);
            textView2.setPadding(0,0,0,50);
            textView2.setTextColor(Color.parseColor("#FF134D7C"));
            textView2.setTextSize(26);
            textView2.setTypeface(textView2.getTypeface(), Typeface.BOLD);
            if (key.equals(rank.get("key"))) {
                textView2.setBackgroundResource(R.drawable.border);
            }

            if (linearLayout != null) {
                linearLayout.addView(textView2);
            }
            count++;
        }
    }
}

