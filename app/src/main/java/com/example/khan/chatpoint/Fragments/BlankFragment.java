package com.example.khan.chatpoint.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.khan.chatpoint.Adapter.chatAdpter;
import com.example.khan.chatpoint.R;
import com.example.khan.chatpoint.dataModels.ContactList;
import com.example.khan.chatpoint.dataModels.Objectchat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class BlankFragment extends Fragment {
    private RecyclerView mblankRecyler;
    private RecyclerView.Adapter mblankadapter;
    private LinearLayoutManager mblankLayoutManager;
    ArrayList<Objectchat> chatlist;


    public BlankFragment() {
        // Required empty public constructor
    }


    public static BlankFragment newInstance() {
        BlankFragment fragment = new BlankFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_blank, container, false);
        chatlist =new ArrayList<>();
        mblankRecyler =view.findViewById(R.id.blankRecycler);
        recyclerView();
      Userchat();
        return view;

    }
    private  void recyclerView(){
        mblankRecyler.setNestedScrollingEnabled(false);
        mblankRecyler.setHasFixedSize(false);
        mblankLayoutManager =new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,false);
        mblankRecyler.setLayoutManager(mblankLayoutManager);
        mblankadapter = new chatAdpter(chatlist);
        mblankRecyler.setAdapter(mblankadapter);



    }
    private void Userchat(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("chat");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snap:dataSnapshot.getChildren()){
                        Objectchat objectchat=new Objectchat(snap.getKey());
                        boolean exit=false;
                        for(Objectchat objectchat1:chatlist) {
                            if (objectchat1.getChatId().equals(objectchat.getChatId()))
                                exit=true;
                        }
                        if(exit)
                            continue;
                        chatlist.add(objectchat);
                        mblankadapter.notifyDataSetChanged();

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
