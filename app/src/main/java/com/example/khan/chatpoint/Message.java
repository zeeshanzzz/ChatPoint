package com.example.khan.chatpoint;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.khan.chatpoint.Adapter.MediaAdpter;
import com.example.khan.chatpoint.Adapter.messageAdapter;
import com.example.khan.chatpoint.Fragments.BlankFragment;
import com.example.khan.chatpoint.Fragments.TAB1;
import com.example.khan.chatpoint.dataModels.MessageObject;
import com.example.khan.chatpoint.dataModels.Objectchat;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Message extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton messageButton,recordButton;
    EditText messageText;
    Button attachbtn;
    private android.support.v7.widget.Toolbar toolbar;
    private int PICK_IMAGE_REQUEST = 1;
    private RecyclerView MrecyclerView,MediaRecyler;
    private LinearLayoutManager Mlayout;
    private LinearLayoutManager Medialayout;
    private MediaAdpter mediaAdpter;
    private messageAdapter MessageAdapter;
    private TextView userview;
   public DatabaseReference reference;
    ArrayList<MessageObject> mlist;
    ArrayList<String> mediaUSerlist=new ArrayList<>();
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
        attachbtn=findViewById(R.id.Attachmentbtn);
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
                attachbtn.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(messageText.length()<=0){
                    recordButton.setVisibility(View.VISIBLE);
                    messageButton.setVisibility(View.GONE);
                    attachbtn.setVisibility(View.VISIBLE);
                }

            }
        });



        initilaizere();
        initilaizmedia();
     getchatmessage();
     attachbtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             recordButton.setVisibility(View.GONE);
             messageButton.setVisibility(View.VISIBLE);
             Opengallery();

         }
     });

    }
    private  void Opengallery(){
        Intent intent = new Intent();

        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


    }
    private  void initilaizmedia(){
       // mlist=new ArrayList<>();
       MediaRecyler=findViewById(R.id.Media_recycler);
       MediaRecyler.setNestedScrollingEnabled(false);
       MediaRecyler.setHasFixedSize(false);
       Medialayout = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        MediaRecyler.setLayoutManager(Medialayout);
        mediaAdpter = new MediaAdpter(this,mediaUSerlist);
        MediaRecyler.setAdapter(mediaAdpter);
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
    ArrayList<String> mediaIdList=new ArrayList<>();
    int total=0;
    private void  sendMessage(){
        final Map hashMap=new HashMap<>();
        String messageId=reference.push().getKey();
        hashMap.put("creator", FirebaseAuth.getInstance().getUid());
        DatabaseReference    myreference=reference.child(messageId);
        if(!messageText.getText().toString().isEmpty()){
            hashMap.put("text",messageText.getText().toString());
       //  myreference.updateChildren(hashMap);

         if(!mediaUSerlist.isEmpty()) {
             for (String mediaUri : mediaUSerlist) {

                 String mediaId = myreference.child("media").push().getKey();
                 mediaIdList.add(mediaId);
                 final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("chat").child(chatid).child(messageId).child(mediaId);
                 UploadTask uploadTask=storageReference.putFile(Uri.parse(mediaUri));
                 uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                         storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                             @Override
                             public void onSuccess(Uri uri) {
                                 hashMap.put("/media/"+mediaIdList.get(total)+"/",uri.toString());
                                 total++;
                                 if(total==mediaIdList.size()){
                                     updateDatabase(reference,hashMap);

                                 }


                             }
                         });

                     }
                 });

             }
         }
         else{
             if(!messageText.getText().toString().isEmpty())
                 updateDatabase(reference,hashMap);

         }


        }


    }
    private void updateDatabase(DatabaseReference reference22,Map map){

        reference.updateChildren(map);
        mediaIdList.clear();
        mediaUSerlist.clear();
        mediaAdpter.notifyDataSetChanged();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK ) {

            Uri uri = data.getData();
            if(data.getClipData()==null)
                mediaUSerlist.add(data.getData().toString());
            else {
                for(int i=0;i<data.getClipData().getItemCount(); i++){
                    mediaUSerlist.add(data.getClipData().getItemAt(i).getUri().toString());

                }


            }
            mediaAdpter.notifyDataSetChanged();



        }
    }


}
