package com.example.khan.chatpoint.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khan.chatpoint.Message;
import com.example.khan.chatpoint.R;
import com.example.khan.chatpoint.dataModels.Objectchat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatAdpter extends RecyclerView.Adapter<chatAdpter.listHolder> {
   public String name1="";
   public String phone="";
    ArrayList<Objectchat> chatlist;
    public chatAdpter(ArrayList<Objectchat> chatlist) {
        this.chatlist =chatlist;
    }
    public class listHolder extends RecyclerView.ViewHolder  {
        public CircleImageView imageView;
        private  TextView name,C_Phone;
        private ConstraintLayout layout;
        public  int MAX_LENGTH=6;
        final View view;

        public listHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.Circular);
            name=itemView.findViewById(R.id.chat_recyler_name);
            layout=itemView.findViewById(R.id.chat_view);
            view = null;
        }


    }

    @NonNull
    @Override
    public listHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_layout,null,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new listHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final listHolder listHolder, final int i) {
       final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("userchats").child(FirebaseAuth.getInstance().getUid()).child(chatlist.get(listHolder.getAdapterPosition()).getChatId());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap:dataSnapshot.getChildren()){
                    if(dataSnapshot.child("otherID")!=null)
                        phone=dataSnapshot.child("otherID").getValue().toString();
                    DatabaseReference reference2=FirebaseDatabase.getInstance().getReference().child("User").child(phone).child("image");
                    reference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String Imageuri="";
                            if(dataSnapshot.getValue()!=null)
                                Imageuri=dataSnapshot.getValue().toString();
                            Picasso.get().load(Imageuri).into(listHolder.imageView);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference reference1= FirebaseDatabase.getInstance().getReference().child("Friends").child(FirebaseAuth.getInstance().getUid()).child(phone).child("name");
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {

                                    if(dataSnapshot.getValue()!=null) {
                                        name1=dataSnapshot.getValue().toString();
                                        listHolder.name.setText(name1);

                                }
                            }


                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //  listHolder.name.setText(chatlist.get(i).getChatId());


        listHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(view.getContext(),Message.class);
                Bundle bundle=new Bundle();
                intent.putExtra("Username",name1);
                intent.putExtra("otherUser",phone);
               // bundle.putString("Username",name1);
                bundle.putString("chatId",chatlist.get(listHolder.getAdapterPosition()).getChatId());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }




}
