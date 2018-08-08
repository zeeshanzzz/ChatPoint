package com.example.khan.chatpoint;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    private DatabaseReference reference;
    private TextView DisplayName,DisplayStatus,once;
  private  Button  friendRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String User_id=getIntent().getStringExtra("userkey");
        DisplayName=findViewById(R.id.Name);
        DisplayStatus=findViewById(R.id.status);
        once=findViewById(R.id.Friends);
        friendRequest=findViewById(R.id.Send);
        reference= FirebaseDatabase.getInstance().getReference("User").child(User_id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String display_name=dataSnapshot.child("name").getValue().toString();
                String status=dataSnapshot.child("status").getValue().toString();
                DisplayName.setText(display_name);
                DisplayStatus.setText(status);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
