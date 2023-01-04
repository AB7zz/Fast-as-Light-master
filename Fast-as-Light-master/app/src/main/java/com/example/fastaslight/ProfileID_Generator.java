package com.example.fastaslight;

import static com.example.fastaslight.HomeMenu.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ProfileID_Generator{
    private int prof_id;

    public ProfileID_Generator(){
        DatabaseReference ref = database.child("LastIDGenerated");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prof_id = snapshot.getValue(Integer.class);
                prof_id++;
                Map<String, Object> date = new HashMap<>();
                date.put("LastIDGenerated",prof_id);
                database.updateChildren(date);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }

    public int getProfileID(){
        return prof_id;
    }


}
