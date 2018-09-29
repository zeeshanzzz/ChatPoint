package com.example.khan.chatpoint;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.khan.chatpoint.dataModels.pageAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class Chat_Activity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager pager;
    private com.example.khan.chatpoint.dataModels.pageAdapter pageAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_);
        pager=findViewById(R.id.viewpager);
        tabLayout=findViewById(R.id.tablayout);
        pageAdapter=new pageAdapter(getSupportFragmentManager());
        pager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(pager);
        toolbar=findViewById(R.id.chat_tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chatpoint");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.chat_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.app_bar_search){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(Chat_Activity.this,MainActivity.class);
            startActivity(intent);
            finish();

        }
        if(item.getItemId()==R.id.app_bar_setting){
            Intent intent=new Intent(Chat_Activity.this,Name.class);
            startActivity(intent);
            finish();
        }
        if(item.getItemId()==R.id.User_id){
            Intent intent=new Intent(Chat_Activity.this,User_profile.class);
            startActivity(intent);
            finish();

        }
        return true;
    }
}
