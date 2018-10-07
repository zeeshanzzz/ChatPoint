package com.example.khan.chatpoint.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.khan.chatpoint.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MediaAdpter extends RecyclerView.Adapter<MediaAdpter.MediaViewHoler> {
    Context context;
    ArrayList<String> medilist;
    ImageView imageView;
    public MediaAdpter(Context context, ArrayList<String> medilist) {
        this.context=context;
        this.medilist=medilist;
    }

    @NonNull
    @Override
    public MediaViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view=   LayoutInflater.from(parent.getContext()).inflate(R.layout.media_layout,null,false);

        return new MediaAdpter.MediaViewHoler(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHoler holder, int position) {
        Context context = null;
       // Glide.with(context).load()
        Picasso.get().load(Uri.parse(medilist.get(position))).into(holder.myimage);

    }

    @Override
    public int getItemCount() {
        return medilist.size();
    }


    public class  MediaViewHoler extends RecyclerView.ViewHolder{
ImageView myimage;

    public MediaViewHoler(View itemView) {
        super(itemView);

        myimage=itemView.findViewById(R.id.media_image);
    }
}
}
