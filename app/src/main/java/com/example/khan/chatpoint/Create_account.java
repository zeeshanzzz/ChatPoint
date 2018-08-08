package com.example.khan.chatpoint;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class Create_account extends AppCompatActivity {
    TextInputLayout email,pass;
    Button log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        email=findViewById(R.id.chat_email);
        pass=findViewById(R.id.chat_password);
        log=findViewById(R.id.Login);
    }
}
