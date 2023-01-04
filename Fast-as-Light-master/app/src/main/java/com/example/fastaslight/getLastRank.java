package com.example.fastaslight;

import static com.example.fastaslight.HomeMenu.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class getLastRank {
    private int maxRank;
    private long maxRankL;
    public getLastRank(){
        DatabaseReference ref = database.child("Leaderboard");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                maxRankL = snapshot.getChildrenCount();
                maxRank = (int) maxRankL;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    public int getlastRankF() {return maxRank;}
}
