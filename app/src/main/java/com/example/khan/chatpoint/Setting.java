package com.example.khan.chatpoint;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Setting extends AppCompatActivity {
    private CircleImageView view;
    private TextView viewName,viewstatus;
    Button btnimage,btnstatus;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    private FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        view=findViewById(R.id.circleImageView);
        viewName=findViewById(R.id.textView2);
        viewstatus=findViewById(R.id.textView3);
        btnimage=findViewById(R.id.chat_change_image);
        btnstatus=findViewById(R.id.chat_change_status);
        user= FirebaseAuth.getInstance().getCurrentUser();
        String user_id=user.getUid();
        reference=FirebaseDatabase.getInstance().getReference("User").child(user_id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue().toString();
                String status=dataSnapshot.child("status").getValue().toString();
                viewName.setText(name);
                viewstatus.setText(status);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
