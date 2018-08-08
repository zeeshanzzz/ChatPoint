package com.example.khan.chatpoint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.khan.chatpoint.R;
import com.example.khan.chatpoint.dataModels.user_profile;
import com.example.khan.chatpoint.holders.holder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecyclerView_users extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private DatabaseReference reference;


   FirebaseRecyclerAdapter<user_profile, holder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_users);
        toolbar = findViewById(R.id.chat_tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("chatpoint");
        recyclerView = findViewById(R.id.main_recyler);
        reference = FirebaseDatabase.getInstance().getReference("User");
       recyclerView.setHasFixedSize(true);

       recyclerView.setLayoutManager( new LinearLayoutManager(this));

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter=new FirebaseRecyclerAdapter<user_profile, holder>(user_profile.class,R.layout.user_layout,holder.class,reference) {
            @Override
            protected void populateViewHolder(holder viewHolder, user_profile model, int position) {
                viewHolder.foodName.setText(model.getName());
     final String key=getRef(position).getKey();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RecyclerView_users.this,Profile.class);
                intent.putExtra("userkey",key);
                startActivity(intent);

            }
        });


            }
        };
        recyclerView.setAdapter(adapter);
    }

}


