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
import com.example.khan.chatpoint.dataModels.Objectchat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class chatAdpter extends RecyclerView.Adapter<chatAdpter.listHolder> {
    ArrayList<Objectchat> chatlist;
    public chatAdpter(ArrayList<Objectchat> chatlist) {
        this.chatlist =chatlist;
    }
    public class listHolder extends RecyclerView.ViewHolder  {
        public ImageView imageView;
        private  TextView name,C_Phone;
        private ConstraintLayout layout;
        public listHolder(@NonNull View itemView) {
            super(itemView);

            //   imageView=itemView.findViewById(R.id.contact_image);
            name=itemView.findViewById(R.id.chat_recyler_name);
            layout=itemView.findViewById(R.id.chat_view);
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
        listHolder.name.setText(chatlist.get(i).getChatId());
        listHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),Message.class);
                Bundle bundle=new Bundle();
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
