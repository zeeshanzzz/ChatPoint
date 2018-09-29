package com.example.khan.chatpoint.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khan.chatpoint.Message;
import com.example.khan.chatpoint.R;
import com.example.khan.chatpoint.dataModels.ContactList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.listHolder> {
    ArrayList<ContactList> userlist;
    public adapter(ArrayList<ContactList> userlist) {
        this.userlist=userlist;
    }
    public class listHolder extends RecyclerView.ViewHolder  {
        public ImageView imageView;
        private  TextView name,C_Phone;
        public ConstraintLayout constraintLayout;
        public  int count=0;


        public listHolder(@NonNull View itemView) {
            super(itemView);

         //   imageView=itemView.findViewById(R.id.contact_image);
            name=itemView.findViewById(R.id.chat_namee);
            C_Phone=itemView.findViewById(R.id.contact_list);
            constraintLayout=itemView.findViewById(R.id.layout1);
        }


    }

    @NonNull
    @Override
    public listHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contacts,null,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
       return new listHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listHolder listHolder, final int i) {
        listHolder.name.setText(userlist.get(i).getName());
        listHolder.C_Phone.setText(userlist.get(i).getPhone());
        listHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key= "";
                boolean exist=true;
                if(exist){
                    exist=false;
                    key= FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();
                   // FirebaseDatabase.getInstance().getReference().child("Friends").child(FirebaseAuth.getInstance().getUid()).child("chat").child(key).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("chat").child(key).setValue(true);
            String receverid=userlist.get(i).getUid();
            String name=userlist.get(i).getName();
                  //  FirebaseDatabase.getInstance().getReference().child("Friends").child(userlist.get(i).getUid()).child("chat").child(key).setValue(true);
                  FirebaseDatabase.getInstance().getReference().child("User").child(userlist.get(i).getUid()).child("chat").child(key).setValue(true);
                    Intent intent = new Intent(view.getContext(), Message.class);
                    intent.putExtra("chatId", key);
                    intent.putExtra("receiver",receverid);
                    intent.putExtra("chatname",name);
                    view.getContext().startActivity(intent);
                }
                else if(exist==false){
                    Intent intent = new Intent(view.getContext(), Message.class);
                    intent.putExtra("chatId", key);
                    view.getContext().startActivity(intent);

                }
                else
                {

                }





                //Bundle bundle=new Bundle();



            }
        });

    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }




}
