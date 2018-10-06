package com.example.khan.chatpoint.Fragments;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.khan.chatpoint.Adapter.adapter;
import com.example.khan.chatpoint.Adapter.chatAdpter;
import com.example.khan.chatpoint.R;
import com.example.khan.chatpoint.dataModels.ContactList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class TAB1 extends Fragment {
    private RecyclerView recyclerView;

    private LinearLayoutManager layout;
    private adapter madapter;
    ArrayList<ContactList> list,contacts;
   // private   ContactList contactList;


    public TAB1() {
        // Required empty public constructor
    }


    public static TAB1 newInstance() {
        TAB1 fragment = new TAB1();
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
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        contacts = new ArrayList<>();
        list=new ArrayList<>();

        recyclerView=view.findViewById(R.id.Tab1_recyler);

      recyclerView.setNestedScrollingEnabled(false);
      layout = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
      recyclerView.setLayoutManager(layout);
      madapter = new adapter(list);
      recyclerView.setAdapter(madapter);
      getContactlist();

      return view;
    }
private void getContactlist(){
    Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
    while (phones.moveToNext()) {

        final String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        String Phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        ContactList  contactList = new ContactList("",name, Phone);
        contacts.add(contactList);
        getUser(contactList);

    }

}
    private void getUser(final ContactList contactList){


        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("User");
        reference.keepSynced(true);
        Query query=reference.orderByChild("phone").equalTo(contactList.getPhone());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String phone="",name ="";
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        if(snapshot.child("phone").getValue()!=null)
                            phone=snapshot.child("phone").getValue().toString();
                        if(snapshot.child("name").getValue()!=null)
                            name=snapshot.child("name").getValue().toString();

                        String mykey=snapshot.getKey();
                        ContactList contactList1= new ContactList(snapshot.getKey(),name,phone);
                        if(name.equals(phone))
                            for (final ContactList mItera : contacts) {
                                if (mItera.getPhone().equals(contactList1.getPhone())) {
                                    contactList1.setName(mItera.getName());

                                }


                                }


                        list.add(contactList1);
                        madapter.notifyDataSetChanged();



                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private  void friends(ContactList contactList){
        DatabaseReference mmreference=FirebaseDatabase.getInstance().getReference().child("Friends").child(contactList.getUid());

    }
}
