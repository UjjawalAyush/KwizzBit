package com.ujjawalayush.example.kwizzbit;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerAdapter4 extends RecyclerView.Adapter<RecyclerAdapter4.RecyclerItemViewHolder3> {

    private ArrayList<RecyclerData4> myList;
    int mLastPosition = 0, request_code = 1;
    long id2;
    int i,j;
    Context context,c;
    String m, n, friend;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,databaseReference1;
    FirebaseUser user;
    public RecyclerAdapter4(ArrayList<RecyclerData4> myList) {
        this.myList = myList;
    }

    public RecyclerItemViewHolder3 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowe, parent, false);
        RecyclerItemViewHolder3 holder = new RecyclerItemViewHolder3(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerItemViewHolder3 holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        holder.etTitleTextView.setText("" + myList.get(position).getUsername());
        mLastPosition = position;
    }

    @Override
    public int getItemCount() {
        return (null != myList ? myList.size() : 0);
    }
    public void notifyData1(ArrayList<RecyclerData4> myList){
        this.myList = myList;
        notifyItemRangeRemoved(0,myList.size());
    }
    public void notifyData(ArrayList<RecyclerData4> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }
    public void clear() {
        int size = myList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                myList.remove(0);
            }

        }
    }
    public class RecyclerItemViewHolder3 extends RecyclerView.ViewHolder{
        private final TextView etTitleTextView;
        LinearLayout mainLayout;
        public String title, description, date;
        Button accept,reject;

        public RecyclerItemViewHolder3(View parent) {
            super(parent);
            etTitleTextView = (TextView) parent.findViewById(R.id.txtTitle8);
            accept=(Button)parent.findViewById(R.id.accept);
            reject=(Button)parent.findViewById(R.id.reject);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c=v.getContext();
                    i=getAdapterPosition();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Friends").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    databaseReference.child(myList.get(i).getName()).child("Username").setValue(myList.get(i).getUsername());
                    databaseReference.child(myList.get(i).getName()).child("Profile Pic").setValue(myList.get(i).getPic());
                    DatabaseReference r=FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    r.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            DatabaseReference databaseReference3=FirebaseDatabase.getInstance().getReference().child("Friends").child(myList.get(i).getName());
                            databaseReference3.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Username").setValue(dataSnapshot.child("Username").getValue().toString());
                            databaseReference3.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile Pic").setValue(dataSnapshot.child("Profile Pic").getValue().toString());
                            DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference().child("Requests").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            databaseReference2.child(myList.get(i).getName()).removeValue();
                            Toast.makeText(c,"Friend successfully added",Toast.LENGTH_LONG).show();
                            Intent data=new Intent(c,Friends.class);
                            c.startActivity(data);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i=getAdapterPosition();
                    DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference().child("Requests").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    databaseReference2.child(myList.get(i).getName()).removeValue();
                    Toast.makeText(v.getContext(),"Friend request successfully rejected",Toast.LENGTH_LONG).show();
                    Intent data=new Intent(v.getContext(),Friends.class);
                    v.getContext().startActivity(data);
                }
            });
        }
    }


    public void UpdateList(ArrayList<RecyclerData4> newList) {
        myList = new ArrayList<>();
        myList.addAll(newList);
        notifyDataSetChanged();
    }
}



