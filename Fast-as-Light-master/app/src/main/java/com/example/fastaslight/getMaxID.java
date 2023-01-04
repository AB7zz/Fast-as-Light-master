package com.example.fastaslight;

import static com.example.fastaslight.HomeMenu.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class getMaxID {
    private int maxid;
    private long maxidL;
    public getMaxID(){
        DatabaseReference ref = database.child("User");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                maxidL = snapshot.getChildrenCount();
                maxid = (int) maxidL;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }

        });
    }

    public int getMaxidF(){return maxid;}
}
