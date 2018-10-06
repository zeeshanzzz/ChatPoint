package com.example.khan.chatpoint;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.khan.chatpoint.Adapter.messageAdapter;
import com.example.khan.chatpoint.Fragments.BlankFragment;
import com.example.khan.chatpoint.Fragments.TAB1;
import com.example.khan.chatpoint.dataModels.MessageObject;
import com.example.khan.chatpoint.dataModels.Objectchat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Message extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton messageButton,recordButton;
    EditText messageText;
    private android.support.v7.widget.Toolbar toolbar;

    private RecyclerView MrecyclerView;
    private LinearLayoutManager Mlayout;
    private messageAdapter MessageAdapter;
    private TextView userview;
   public DatabaseReference reference;
    ArrayList<MessageObject> mlist;
  public  String Chatid,chatid,receverId,Username,Friend;
    String  name_of_user;
  private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        if(getIntent()!=null)
            chatid="";
        receverId="";
        name_of_user="";
        Friend="";
        Username="";
        Friend=getIntent().getStringExtra("otherUser");
        receverId=getIntent().getStringExtra("receiver");
        chatid=getIntent().getStringExtra("chatId");
        name_of_user=getIntent().getStringExtra("chatname");
        Username=getIntent().getStringExtra("Username");

        if(getIntent()!=null)
            Chatid="";
        Chatid=getIntent().getStringExtra("chatId");
        toolbar=findViewById(R.id.message_toolbar);
        setSupportActionBar(toolbar);
    ActionBar actionBar= getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setDisplayShowCustomEnabled(true);
    actionBar.setTitle(name_of_user);
   // actionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater layoutInflater= (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.customtool,null);
        userview=view.findViewById(R.id.Username);
        userview.setText(Username);
        userview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Message.this,Contact_profile.class);
                intent.putExtra("otherman",Friend);
                intent.putExtra("AgainName",Username);
                startActivity(intent);
            }
        });
        actionBar.setCustomView(view);


      //  if(!Chatid.isEmpty() && Chatid!=null){
        reference=FirebaseDatabase.getInstance().getReference().child("chat").child(Chatid);
        reference.keepSynced(true);
        messageText=findViewById(R.id.messagetext);
        messageText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                messageButton.setVisibility(View.VISIBLE);
                recordButton.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        messageButton=findViewById(R.id.messagebutton);
        recordButton=findViewById(R.id.recordingbutton);
        messageButton.setOnClickListener(this);
      // String mytext= messageText.getText().toString().trim();
        messageText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(messageText.length()<=0){
                    recordButton.setVisibility(View.VISIBLE);
                    messageButton.setVisibility(View.GONE);
                }

            }
        });



        initilaizere();
     getchatmessage();

    }
    private  void initilaizere(){
         mlist=new ArrayList<>();
        MrecyclerView=findViewById(R.id.message_recycler);
        MrecyclerView.setNestedScrollingEnabled(false);
        Mlayout = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        MrecyclerView.setLayoutManager(Mlayout);
      MessageAdapter = new messageAdapter(mlist);
        MrecyclerView.setAdapter(MessageAdapter);
    }
    private void  sendMessage(){
        if(!messageText.getText().toString().isEmpty()){
     DatabaseReference    myreference=reference.push();
            Map hashMap=new HashMap<>();
            hashMap.put("text",messageText.getText().toString());
            hashMap.put("creator", FirebaseAuth.getInstance().getUid());
         myreference.updateChildren(hashMap);


        }


    }

    @Override
    public void onClick(View view) {
        if(view==messageButton) {
            Toast.makeText(Message.this, "Message Sent", Toast.LENGTH_LONG).show();
            sendMessage();
            messageText.setText("");
        }

    }
    private void getchatmessage(){
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    String text="";
                    String creator="";
                    if(dataSnapshot.child("text").getValue()!=null)
                        text=dataSnapshot.child("text").getValue().toString();
                    if(dataSnapshot.child("creator").getValue()!=null)
                        creator=dataSnapshot.child("creator").getValue().toString();

                    MessageObject messageObject=new MessageObject(creator,text);
                    mlist.add(messageObject);
                    Mlayout.scrollToPosition(mlist.size()-1);
                    MessageAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


}
