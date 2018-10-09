package com.example.khan.chatpoint;

import android.app.SearchManager;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.khan.chatpoint.dataModels.pageAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class Chat_Activity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager pager;
    private com.example.khan.chatpoint.dataModels.pageAdapter pageAdapter;
    private TabLayout tabLayout;
   // public  SearchView searchView;
    //public  SearchView searchView;
    public android.support.v7.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_);
        pager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);
        pageAdapter = new pageAdapter(getSupportFragmentManager());
        pager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(pager);
        toolbar = findViewById(R.id.chat_tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chatpoint");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);

        if (searchItem != null) {
            searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
            searchView.setOnCloseListener(new android.support.v7.widget.SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    //some operation
                    return false;
                }
            });
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //some operation
                }
            });
            EditText searchPlate = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchPlate.setHint("Search");
            View searchPlateView = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            searchPlateView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
            // use this method for search process
            searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(Chat_Activity.this, query, Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            } );
            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        }
        return true;

    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.app_bar_search) {
            //    FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Chat_Activity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
        if (item.getItemId() == R.id.app_bar_setting) {
            Intent intent = new Intent(Chat_Activity.this, Name.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.User_id) {
            Intent intent = new Intent(Chat_Activity.this, User_profile.class);
            startActivity(intent);
            finish();

        }
        if (item.getItemId() == R.id.action_search) {

        }

            return true;
        }

}
