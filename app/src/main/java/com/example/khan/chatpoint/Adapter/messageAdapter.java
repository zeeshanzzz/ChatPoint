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

import com.daasuu.bl.BubbleLayout;
import com.example.khan.chatpoint.R;
import com.example.khan.chatpoint.dataModels.MessageObject;
import com.github.library.bubbleview.BubbleDrawable;
import com.github.library.bubbleview.BubbleTextView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class messageAdapter extends RecyclerView.Adapter<messageAdapter.MessageViewHolder> {
    ArrayList<MessageObject> messagelist;
    private BubbleDrawable.ArrowLocation mArrowLocation;
    private FirebaseAuth firebaseAuth;
    public messageAdapter(ArrayList<MessageObject> messagelist) {
        this.messagelist =messagelist;
    }
    public class MessageViewHolder extends RecyclerView.ViewHolder  {
        public ImageView imageView;
        private BubbleTextView bubbleTextView;
        public ConstraintLayout constraintLayout;
        public BubbleLayout bubbleLayout;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            //   imageView=itemView.findViewById(R.id.contact_image);
          //  bubbleTextView.buildDrawingCache();
        //    bubbleDrawable=new BubbleDrawable.Builder().bubbleColor(R.color.colorwhite);
            bubbleTextView=itemView.findViewById(R.id.Mymessage);
            constraintLayout=itemView.findViewById(R.id.MessageLayout);
            bubbleLayout=itemView.findViewById(R.id.bubbli);
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
         if(messageId.equals(my)){
             //listHolder.mMessage.setBackgroundResource(R.color.colorwhite);
             int left,  right,  top,  bottom;
             RectF mrect=new RectF();
             BubbleDrawable bubbleDrawable=new BubbleDrawable.Builder().rect(mrect).bubbleColor(R.color.colorwhite).build();
             listHolder.bubbleTextView.getDrawableState();
             listHolder.bubbleTextView.setTextColor(Color.BLACK);
             listHolder.bubbleLayout.setBubbleColor(R.color.colorwhite);
             

         }
         else {
         //    listHolder.bubbleDrawable=new BubbleDrawable.Builder().bubbleColor(R.color.colorPrimary);
             listHolder.bubbleTextView.setTextColor(Color.WHITE);
             listHolder.bubbleTextView.setGravity(Gravity.LEFT);
         }
        listHolder.bubbleTextView.setText(messagelist.get(i).getMessage());
       // listHolder.mSender.setText(messagelist.get(i).getMessageId());
       // listHolder.mSender.setText(messagelist.get(i).getSenderId());

    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }




}
