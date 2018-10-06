package com.example.khan.chatpoint.Adapter;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daasuu.bl.ArrowDirection;
import com.daasuu.bl.BubbleLayout;
import com.example.khan.chatpoint.R;
import com.example.khan.chatpoint.dataModels.MessageObject;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class messageAdapter extends RecyclerView.Adapter<messageAdapter.MessageViewHolder> {
    ArrayList<MessageObject> messagelist;
    public Boolean bBoolean=false;

    private FirebaseAuth firebaseAuth;
    public messageAdapter(ArrayList<MessageObject> messagelist) {
        this.messagelist =messagelist;
    }
    public class MessageViewHolder extends RecyclerView.ViewHolder  {
        public ImageView imageView;
        public TextView view;
        public ConstraintLayout constraintLayout;
        public LinearLayout linearLayout;
        public BubbleLayout bubbleLayout;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.MessageLayout);
            bubbleLayout=itemView.findViewById(R.id.bubbli);
            view=itemView.findViewById(R.id.messageview);

        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.messagelayout,null,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new MessageViewHolder(view);
    }

    @SuppressLint({"ResourceAsColor", "RtlHardcoded"})
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder listHolder, final int i) {
        String messageId=messagelist.get(i).getMessageId();
         String my=FirebaseAuth.getInstance().getCurrentUser().getUid();
        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);;
        boolean aBoolean=false;
         if(messageId.equals(my)){

            DejaVu(listHolder,lp);



             lp.gravity= Gravity.RIGHT;
             listHolder.view.setTextColor(R.color.black);
             listHolder.bubbleLayout.setBubbleColor(Color.WHITE);
             listHolder.bubbleLayout.setLayoutParams(lp);
             //listHolder.bubbleLayout.setLayoutParams(lp);
             listHolder.bubbleLayout.setArrowDirection(ArrowDirection.RIGHT);


         }
         else {

            listHolder.view.setTextColor(Color.WHITE);
            listHolder.bubbleLayout.setArrowDirection(ArrowDirection.LEFT);
         }
       listHolder.view.setText(messagelist.get(i).getMessage());
       // listHolder.mSender.setText(messagelist.get(i).getMessageId());
       // listHolder.mSender.setText(messagelist.get(i).getSenderId());

    }
    public void DejaVu(MessageViewHolder messageViewHolder,LinearLayout.LayoutParams layoutParams){
       // LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //   lp.setMargins(100,300,2,0);
        if(bBoolean==false){
            //   lp.setMargins(100,300,2,0);
            layoutParams.topMargin=300;
            layoutParams.rightMargin=0;
            messageViewHolder.bubbleLayout.setLayoutParams(layoutParams);
            bBoolean=true;
        }
        else {
            layoutParams.topMargin = 30;
            layoutParams.rightMargin = 0;

        }
      // messageViewHolder.bubbleLayout.setLayoutParams(lp);


    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }




}
