package com.example.khan.chatpoint.holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.khan.chatpoint.R;

public class holder extends RecyclerView.ViewHolder {
   public TextView foodName;
    public holder(@NonNull View itemView) {
        super(itemView);
        foodName=itemView.findViewById(R.id.chat_recyler_name);


    }
}
