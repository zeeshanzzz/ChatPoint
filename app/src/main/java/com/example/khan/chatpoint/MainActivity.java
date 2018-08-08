package com.example.khan.chatpoint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button b1,b2;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=findViewById(R.id.Chat_need_btn);
        b2=findViewById(R.id.Chat_already_btn);
        imageView=findViewById(R.id.imageView);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==b1){
            Intent intent=new Intent(MainActivity.this,Phone.class);

            startActivity(intent);
            finish();
        }
        if(view==b2){

            Intent intent=new Intent(MainActivity.this,Phone.class);
            startActivity(intent);
            finish();
        }
    }
}
