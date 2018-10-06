package com.example.khan.chatpoint;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Contact_profile extends AppCompatActivity {
    Toolbar toolbar;
    public  String Userprofiler,UserName1;
    private TextView view,Phoneview;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_profile);
        if (getIntent() != null)
            Userprofiler = "";
        UserName1="";
        Userprofiler = getIntent().getStringExtra("otherman");
        UserName1=getIntent().getStringExtra("AgainName");
        toolbar = findViewById(R.id.contact_toolbar);
        getSupportActionBar();
        setSupportActionBar(toolbar);
        imageView=findViewById(R.id.usr_image);
        view = findViewById(R.id.Profile_Name);
        view.setText(UserName1);
        Phoneview=findViewById(R.id.phoneView);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User").child(Userprofiler);
        Query query = reference.orderByChild("Name");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name22 ="",Phone="",image="";
               // name22=dataSnapshot.child("Name").getValue().toString();
                //view.setText(name22);
                image=dataSnapshot.child("image").getValue().toString();
                Glide.with(Contact_profile.this).load(image).into(imageView);
                if(dataSnapshot.child("phone").getValue()!=null)
                    Phone=dataSnapshot.child("phone").getValue().toString();
                    Phoneview.setText(Phone);






            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
