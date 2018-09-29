package com.example.khan.chatpoint;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   private Button b1,b2;
    public ImageView imageView;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        b1=findViewById(R.id.Chat_need_btn);
        b2=findViewById(R.id.Chat_already_btn);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseUser user=auth.getCurrentUser();
        if(user!=null){
            Intent intent=new Intent(MainActivity.this,Chat_Activity.class);
            startActivity(intent);
            finish();
        }


        getPermission();
    }

    @Override
    public void onClick(View view) {
        if(view==b1){

            Intent intent=new Intent(MainActivity.this,Create_account.class);
            startActivity(intent);

        }
        if(view==b2){


            Intent intent=new Intent(MainActivity.this,Name.class);
            startActivity(intent);
        }
    }
    private void getPermission(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.READ_CONTACTS},1);
        }
    }
}
